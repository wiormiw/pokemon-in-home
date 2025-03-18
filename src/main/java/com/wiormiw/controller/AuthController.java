package com.wiormiw.controller;

import com.wiormiw.dto.AuthResponse;
import com.wiormiw.dto.LoginDTO;
import com.wiormiw.dto.RegisterDTO;
import com.wiormiw.service.AuthService;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import jakarta.validation.Valid;

@Path("/api/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthController {
    @Inject
    AuthService authService;

    @POST
    @Path("/register")
    public Response register(@Valid RegisterDTO dto) {
        AuthResponse tokens = authService.register(dto.username(), dto.password());
        return Response.ok(tokens).build();
    }

    @POST
    @Path("/login")
    public Response login(@Valid LoginDTO dto) {
        AuthResponse tokens = authService.login(dto.username(), dto.password());
        return Response.ok(tokens).build();
    }

    @POST
    @Path("/refresh")
    public Response refresh(@QueryParam("refreshToken") String refreshToken) {
        if (refreshToken == null) {
            throw new WebApplicationException("Refresh token missing", Response.Status.BAD_REQUEST);
        }
        return authService.refresh(refreshToken);
    }

    @POST
    @Path("/refresh-token")
    public Response refreshToken(@QueryParam("refreshToken") String refreshToken) {
        if (refreshToken == null) {
            throw new WebApplicationException("Refresh token missing", Response.Status.BAD_REQUEST);
        }
        AuthResponse tokens = authService.refreshToken(refreshToken);
        return Response.ok(tokens).build();
    }

    @POST
    @Path("/logout")
    @Authenticated
    public Response logout(@Context SecurityContext ctx) {
        Long userId = Long.valueOf(ctx.getUserPrincipal().getName());
        authService.logout(userId);
        return Response.ok().build();
    }
}
