package com.wiormiw.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CustomExceptionMapper implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception exception) {
        String message = exception.getMessage() != null ? exception.getMessage() : "Unknown error";
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse("ERROR", message))
                .build();
    }
}
