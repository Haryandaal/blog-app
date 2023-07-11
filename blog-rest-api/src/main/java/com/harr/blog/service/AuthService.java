package com.harr.blog.service;

import com.harr.blog.dtos.LoginDto;
import com.harr.blog.dtos.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}
