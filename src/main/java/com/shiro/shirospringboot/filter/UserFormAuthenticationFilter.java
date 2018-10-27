package com.shiro.shirospringboot.filter;

import com.shiro.shirospringboot.bean.User;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


//自定义认证过滤器，加入rememberMe功能
public class UserFormAuthenticationFilter extends FormAuthenticationFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Subject subject = getSubject(request,response);
//        如果isAccessAllowed为false证明不是登录过的，同时isRememberd 为true
//        证明是没有登陆直接通过记住我功能进来的
        if (!subject.isAuthenticated() && subject.isRemembered()){
//            获取session看看是否为空
            Session session = subject.getSession(true);
//            查看session的属性当前是否为空
            if(session.getAttribute("userName") == null){
//                如果为空的才初始化
                User dbUser = (User) subject.getPrincipal();
//           存入用户数据
                session.setAttribute("userName",dbUser.getName());
            }
        }
        /**
         * 这种方法本来只返回subject.isAuthenticated(),现在加上subject.isRemembered()
         * 让他同时兼容remember这种情况
         */
        return subject.isAuthenticated() || subject.isRemembered();
//        return super.isAccessAllowed(request, response, mappedValue);
    }
}
