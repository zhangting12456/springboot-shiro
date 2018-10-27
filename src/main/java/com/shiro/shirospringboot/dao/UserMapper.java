package com.shiro.shirospringboot.dao;

import com.shiro.shirospringboot.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT id,name,password FROM shiro.t_user where name = #{name}")
    public User findByName(String name);

    @Select("SELECT p.permission\n" +
            "\t     FROM t_user u\n" +
            "\t         INNER JOIN t_user_role ur ON u.id = ur.user_id\n" +
            "\t         INNER JOIN t_role_permission rp ON ur.role_id = rp.role_id\n" +
            "\t         INNER JOIN t_permission p ON rp.permission_id = p.id\n" +
            "\t     WHERE  u.id = #{value}")
  public List<String> findPermissionByUserId(Integer id);
}
