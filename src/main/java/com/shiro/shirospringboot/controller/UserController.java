package com.shiro.shirospringboot.controller;

import com.shiro.shirospringboot.bean.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class UserController {
    @RequestMapping("/login")
    public String login(User user, String rememberMe ,HttpServletRequest request, Model model){
        Subject subject = SecurityUtils.getSubject();
        Md5Hash hash = new Md5Hash(user.getPassword(),user.getName(),2);
//        AuthenticationToken token = new UsernamePasswordToken(user.getName(),user.getPassword());
        AuthenticationToken token = new UsernamePasswordToken(user.getName(),hash.toString());
      //  System.out.println(user.getUsername()+"  "+user.getPassword());
//        设置rememberMe的功能
        if(rememberMe!=null && rememberMe.equals("1")){
            ((UsernamePasswordToken) token).setRememberMe(true);
        }
        try {
            subject.login(token);
           User dbUser = (User) subject.getPrincipal();
          //  System.out.println(dbUser.getName());
            request.getSession().setAttribute("userName",dbUser.getName());
            return "redirect:/index";
        } catch (UnknownAccountException e){
            model.addAttribute("msg","用户名不存在");
            return "login";
        }catch (IncorrectCredentialsException e){
            model.addAttribute("msg","密码错误");
            return "login";
        }
    }
    @RequestMapping("/logout")
    public String logout(){
        Subject Subject = SecurityUtils.getSubject();
        Subject.logout();
        return "redirect:/toLogin";
    }
}
