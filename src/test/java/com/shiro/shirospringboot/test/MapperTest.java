package com.shiro.shirospringboot.test;

import com.shiro.shirospringboot.ShiroSpringbootApplication;
import com.shiro.shirospringboot.bean.User;
import com.shiro.shirospringboot.dao.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ShiroSpringbootApplication.class)
public class MapperTest {
    @Resource
    private UserMapper UserMapper;

    @Test
    public void testFindByName(){
        User user = UserMapper.findByName("eric");
        System.out.println(user);
    }
}
