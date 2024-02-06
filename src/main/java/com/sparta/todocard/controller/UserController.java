package com.sparta.todocard.controller;

import com.sparta.todocard.dto.LoginRequestDto;
import com.sparta.todocard.dto.LoginResponseDto;
import com.sparta.todocard.dto.SignupRequestDto;
import com.sparta.todocard.dto.SignupResponseDto;
import com.sparta.todocard.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;

    @CrossOrigin
    @PostMapping("/signup")
    public SignupResponseDto signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        log.info("Signup request received for username: {}", signupRequestDto.getUsername());
        userService.signup(signupRequestDto);
        return new SignupResponseDto("회원가입", 200);
    }
    @ResponseBody
    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody @Valid LoginRequestDto loginRequestDto, HttpServletResponse response) {
        userService.login(loginRequestDto, response);
        return new LoginResponseDto("로그인", 200);
    }
}