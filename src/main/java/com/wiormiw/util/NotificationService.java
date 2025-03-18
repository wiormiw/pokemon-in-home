package com.wiormiw.util;

import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.pubsub.PubSubCommands;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class NotificationService {
    private final RedisDataSource redisDataSource;
    private final PubSubCommands<String> pubSubCommands;

    @Inject
    public NotificationService(RedisDataSource redisDataSource) {
        this.redisDataSource = redisDataSource;
        this.pubSubCommands = redisDataSource.pubsub(String.class);
    }

    public void sendNotification(Long userId, String message) {
        pubSubCommands.publish("notifications:" + userId, message);
    }
}
