package com.wiormiw.service;

import com.wiormiw.dto.AuthResponse;
import com.wiormiw.entity.User;
import com.wiormiw.exception.ErrorResponse;
import com.wiormiw.exception.PokemonException;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.value.ValueCommands;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.Duration;
import java.util.UUID;

@ApplicationScoped
public class AuthService {
    private final RedisDataSource redisDataSource;
    private final ValueCommands<String, String> redisCommands;

    @ConfigProperty(name = "quarkus.smallrye-jwt.issuer")
    String issuer;

    @ConfigProperty(name = "quarkus.smallrye-jwt.expires-in")
    long accessTokenExpiresInSeconds;

    private static final long REFRESH_TOKEN_EXPIRES_IN_SECONDS = 24 * 3600;

    @Inject
    public AuthService(RedisDataSource redisDataSource) {
        this.redisDataSource = redisDataSource;
        this.redisCommands = redisDataSource.value(String.class);
    }

    public AuthResponse register(String username, String password) {
        User user = new User();
        user.username = username;
        user.password = BcryptUtil.bcryptHash(password);
        user.persist();
        return generateTokens(user.id);
    }

    public AuthResponse login(String username, String password) {
        User user = User.find("username", username).firstResult();
        if (user != null && BcryptUtil.matches(password, user.password)) {
            return generateTokens(user.id);
        }
        throw new PokemonException("Invalid credentials");
    }

    public Response refresh(String refreshToken) {
        String userIdStr = redisCommands.get("refresh:" + refreshToken);
        if (userIdStr == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new ErrorResponse("INVALID_REFRESH_TOKEN", "Refresh token is invalid or expired"))
                    .build();
        }
        Long userId = Long.valueOf(userIdStr);
        String newAccessToken = Jwt.issuer(issuer)
                .subject(String.valueOf(userId))
                .claim("user_id", userId)
                .expiresIn(Duration.ofSeconds(accessTokenExpiresInSeconds))
                .sign();
        redisCommands.setex("token:" + userId, accessTokenExpiresInSeconds, newAccessToken);
        return Response.ok(new AuthResponse(newAccessToken, null)).build();
    }

    public AuthResponse refreshToken(String refreshToken) {
        String userIdStr = redisCommands.get("refresh:" + refreshToken);
        if (userIdStr == null) {
            throw new PokemonException("Refresh token is invalid or expired");
        }
        Long userId = Long.valueOf(userIdStr);
        redisCommands.getdel("refresh:" + refreshToken);
        return generateTokens(userId);
    }

    public void logout(Long userId) {
        redisCommands.getdel("token:" + userId);
        redisCommands.getdel("refresh:" + redisCommands.get("refresh:" + userId));
    }

    private AuthResponse generateTokens(Long userId) {
        String accessToken = Jwt.issuer(issuer)
                .subject(String.valueOf(userId))
                .claim("user_id", userId)
                .expiresIn(Duration.ofSeconds(accessTokenExpiresInSeconds))
                .sign();

        String refreshToken = UUID.randomUUID().toString();

        redisCommands.setex("token:" + userId, accessTokenExpiresInSeconds, accessToken);
        redisCommands.setex("refresh:" + refreshToken, REFRESH_TOKEN_EXPIRES_IN_SECONDS, String.valueOf(userId));

        return new AuthResponse(accessToken, refreshToken);
    }
}