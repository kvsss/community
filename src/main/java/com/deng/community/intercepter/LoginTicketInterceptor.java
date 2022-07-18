package com.deng.community.intercepter;

import com.deng.community.controller.UserController;
import com.deng.community.entity.LoginTicket;
import com.deng.community.entity.User;
import com.deng.community.service.UserService;
import com.deng.community.util.CookieUtil;
import com.deng.community.util.HostHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author :deng
 * @version :1.0
 * @description :
 * @since :1.8
 */


@Component
public class LoginTicketInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    //private static final Logger logger = LoggerFactory.getLogger(LoginTicketInterceptor.class);


/*    @Autowired
    private LoginTicket*/

    /**
     * 对于每一次连接，都缓存了一个user对象,结束时销毁
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从cookie中获取凭证
        String ticket = CookieUtil.getValue(request, "ticket");

        if (ticket != null) {
            // 查询凭证
            LoginTicket loginTicket = userService.findLoginTicket(ticket);
            // 检查凭证是否有效
            if (loginTicket != null && loginTicket.getStatus() == 0) {

                // 还没有过期
                if (loginTicket.getExpired().after(new Date())) {
/*                    Date expired = loginTicket.getExpired();
                    Date date = new Date();
                    boolean flag = expired.after(date);*/

                    // 根据凭证查询用户
                    User user = userService.findUserById(loginTicket.getUserId());
                    // 在本次请求中持有用户
                    hostHolder.setUser(user);
                } else { //过期

                    // 重定向到首页
                    response.sendRedirect(request.getContextPath() + "/login");
                    return false;
                    // 过期了
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        User user = hostHolder.getUser();
        if (user != null && modelAndView != null) {
            modelAndView.addObject("loginUser", user);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.clear();
    }
}

