package com.wiormiw.dto;

public record AuthResponse(
        String accessToken,
        String refreshToken
) {}
