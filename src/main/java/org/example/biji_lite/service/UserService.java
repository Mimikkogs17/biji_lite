package org.example.biji_lite.service;

import org.example.biji_lite.common.Result;
import org.example.biji_lite.dto.UserLoginDTO;
import org.example.biji_lite.dto.UserRegisterDTO;
import org.example.biji_lite.entity.User;
import jakarta.servlet.http.HttpSession;

public interface UserService {

    Result<Void> register(UserRegisterDTO registerDTO);

    Result<User> login(UserLoginDTO loginDTO, HttpSession session);

    User getUserById(Long id);
}