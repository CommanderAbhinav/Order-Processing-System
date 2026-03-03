package com.abhinav.order_service.dto;

public class ErrorResponse {

    private boolean success;
    private String message;
    private int status;
    private String path;

    public ErrorResponse(
            boolean success,
            String message,
            int status,
            String path) {

        this.success = success;
        this.message = message;
        this.status = status;
        this.path = path;
    }
}
