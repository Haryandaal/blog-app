package com.harr.blog.dtos;

import org.springframework.http.HttpStatus;

import java.util.Date;

public class ErrorDetails {
    private Date timestamp;
    private HttpStatus status;
    private String message;
    private String details;

    public ErrorDetails(Date timestamp, HttpStatus status, String message, String details) {
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
        this.details = details;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}
