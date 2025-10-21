package com.luvsic.stflserverapp.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.luvsic.stflserverapp.custom.exception.BusinessException;
import com.luvsic.stflserverapp.entity.User;
import com.luvsic.stflserverapp.mapper.UserDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author zyy
 * @version v1.0.0
 * @date 2025/10/21 16:42
 * @description
 */
@Service("authService")
@RequiredArgsConstructor
public class AuthService {
    private final UserDAO userDAO;

    public void register(User user) {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getUsername, user.getUsername());
        if (userDAO.selectOne(lqw) != null) {
            throw new BusinessException("用户已存在");
        }
        user.setId(null);
        userDAO.insert(user);
    }

    public void login(String username, String password) {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getUsername, username).eq(User::getPassword, password);
        User user = userDAO.selectOne(lqw);
        if (user == null) {
            throw new BusinessException("用户不存在或用户名密码错误");
        }
    }
}
