package com.harr.blog.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {
    private Long id;

//    name should not be null or empty
    @NotEmpty(message = "name is required")
    private String name;

//    email should not be null or empty
//    email field validation
    @NotEmpty(message = "email is required")
    @Email
    private String email;

//    body should not be null or empty
    @NotEmpty(message = "body is required")
    @Size(min = 10, message = "body must be at least 10 characters")
    private String body;
}
