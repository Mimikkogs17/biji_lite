package org.example.biji_lite.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.example.biji_lite.common.BusinessException;
import org.example.biji_lite.common.Result;
import org.example.biji_lite.dto.UserLoginDTO;
import org.example.biji_lite.dto.UserRegisterDTO;
import org.example.biji_lite.entity.User;
import org.example.biji_lite.mapper.UserMapper;
import org.example.biji_lite.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Result<Void> register(UserRegisterDTO registerDTO) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, registerDTO.getUsername());
        Long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            return Result.error(400, "用户名已被注册");
        }

        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setNickname(registerDTO.getNickname());

        userMapper.insert(user);
        return Result.success("注册成功", null);
    }

    @Override
    public Result<User> login(UserLoginDTO loginDTO, HttpSession session) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, loginDTO.getUsername());
        User user = userMapper.selectOne(queryWrapper);

        if (user == null) {
            return Result.error(400, "用户名或密码错误");
        }

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            return Result.error(400, "用户名或密码错误");
        }

        session.setAttribute("userId", user.getId());

        user.setPassword(null);
        return Result.success("登录成功", user);
    }

    @Override
    public User getUserById(Long id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new BusinessException(404, "用户不存在");
        }
        return user;
    }
}