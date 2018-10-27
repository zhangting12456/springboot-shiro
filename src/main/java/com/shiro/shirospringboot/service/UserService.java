package com.shiro.shirospringboot.service;

import com.shiro.shirospringboot.bean.User;
import com.shiro.shirospringboot.dao.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    public User findByName(String name){
        User user = userMapper.findByName(name);
        return user;
    }
    public List<String> findPermissionByUserId(Integer id){
        List<String> prems = userMapper.findPermissionByUserId(id);
        return prems;
    }
}
