package com.sparta.todocard.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequestDto {
    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private String username;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String password;
}

