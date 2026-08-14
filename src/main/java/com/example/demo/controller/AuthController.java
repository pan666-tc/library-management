package com.example.demo.controller;

import com.example.demo.Result.result;
import com.example.demo.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@Tag(name = "用户认证", description = "注册、登录接口")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @Operation(summary = "用户注册")
    public result<String> register(
            @Parameter(description = "用户名") @RequestParam String username,
            @Parameter(description = "密码") @RequestParam String password,
            @Parameter(description = "手机号（选填）") @RequestParam(required = false) String phone) {
        authService.register(username, password, phone);
        return result.success("注册成功");
    }

    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "校验成功后返回 JWT token，后续请求需在 Header 中携带")
    public result<Map<String, Object>> login(
            @Parameter(description = "用户名") @RequestParam String username,
            @Parameter(description = "密码") @RequestParam String password) {
        Map<String, Object> data = authService.login(username, password);
        return result.success(data);
    }
}
