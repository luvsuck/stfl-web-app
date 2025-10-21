package com.luvsic.stflserverapp.controller;

import com.luvsic.stflserverapp.custom.R;
import com.luvsic.stflserverapp.entity.User;
import com.luvsic.stflserverapp.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zyy
 * @version v1.0.0
 * @date 2025/10/21 16:43
 * @description
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public R<String> register(@RequestBody User user) {
        authService.register(user);
        return R.success("ok");
    }

    @PostMapping("/login")
    public R<String> login(@RequestBody User user) {
        authService.login(user.getUsername(), user.getPassword());
        return R.success("ok");
    }
}
