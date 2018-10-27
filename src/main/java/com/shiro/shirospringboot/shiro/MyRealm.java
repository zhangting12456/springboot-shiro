package com.shiro.shirospringboot.shiro;

import com.shiro.shirospringboot.bean.User;
import com.shiro.shirospringboot.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

public class MyRealm extends AuthorizingRealm {
    @Resource
    private UserService userService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        /*info.addStringPermission("product:add");*/
        Subject subject = SecurityUtils.getSubject();
        User dbUser = (User) subject.getPrincipal();
        List<String> perms = userService.findPermissionByUserId(dbUser.getId());
        if(perms!=null){
            for (String perm : perms){
                if(!StringUtils.isEmpty(perm)){
                    info.addStringPermission(perm);
                }
            }
        }
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
//        System.out.println(token.getUsername());
       /* String user = "jack";
        String password = "1234";
        User dbuser = new User();
        dbuser.setUsername(user);
        dbuser.setPassword(password);
        if(!token.getUsername().equals(user)){
            return null;
        }*/
       User dbUser = userService.findByName(token.getUsername());
        System.out.println(dbUser.getName());
       if(dbUser==null){
           return null;
       }
        return new SimpleAuthenticationInfo(dbUser,dbUser.getPassword(),"");
    }
}
