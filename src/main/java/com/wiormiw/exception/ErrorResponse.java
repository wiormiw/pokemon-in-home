package com.wiormiw.exception;

public record ErrorResponse(
        String status,
        String message
) {}
