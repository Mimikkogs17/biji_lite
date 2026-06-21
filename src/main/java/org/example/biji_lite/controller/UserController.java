package org.example.biji_lite.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.biji_lite.common.Result;
import org.example.biji_lite.dto.UserLoginDTO;
import org.example.biji_lite.dto.UserRegisterDTO;
import org.example.biji_lite.entity.User;
import org.example.biji_lite.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public Result<Void> register(@Valid @RequestBody UserRegisterDTO registerDTO) {
        return userService.register(registerDTO);
    }

    @PostMapping("/login")
    public Result<User> login(@Valid @RequestBody UserLoginDTO loginDTO, HttpSession session) {
        return userService.login(loginDTO, session);
    }

    @GetMapping("/info")
    public Result<User> info(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return Result.error(401, "未登录，请先登录");
        }
        User user = userService.getUserById(userId);
        user.setPassword(null);
        return Result.success(user);
    }

    @GetMapping("/{id}")
    public Result<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        user.setPassword(null);
        return Result.success(user);
    }
}