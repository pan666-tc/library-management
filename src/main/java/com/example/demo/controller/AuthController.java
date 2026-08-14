package com.example.demo.controller;

import com.example.demo.Result.result;
import com.example.demo.service.AuthService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * 注册接口
     * POST /auth/register?username=xxx&password=xxx&phone=xxx
     */
    @PostMapping("/register")
    public result<String> register(@RequestParam String username,
                                   @RequestParam String password,
                                   @RequestParam(required = false) String phone) {
        authService.register(username, password, phone);
        return result.success("注册成功");
    }

    /**
     * 登录接口
     * POST /auth/login?username=xxx&password=xxx
     * 返回 JWT token
     */
    @PostMapping("/login")
    public result<Map<String, Object>> login(@RequestParam String username,
                                              @RequestParam String password) {
        Map<String, Object> data = authService.login(username, password);
        return result.success(data);
    }
}
