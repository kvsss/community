package com.deng.community.temp;

import com.deng.community.advice.ExceptionAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author :deng
 * @version :1.0
 * @since :1.8
 */

@Controller
@RequestMapping("/temp")
public class TempController {


    private static final Logger logger = LoggerFactory.getLogger(TempController.class);


    // 参数拼接
    // student?current=10&limit=1
    @RequestMapping(path = "/student", method = RequestMethod.GET)
    @ResponseBody
    public String f1(
            @RequestParam(name = "current", required = false, defaultValue = "1") int current,
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit) {
        return "";
    }

    // restful 风格
    @RequestMapping(path = "f2/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String f2(
            @PathVariable("id") int id) {
        return "";
    }

    // 返回字符串
    @RequestMapping(path = "/s", method = RequestMethod.POST)
    @ResponseBody
    public String f2(String name, String password) {
        System.out.println(name);
        System.out.println(password);
        return "";
    }

    // 返回视图
    @RequestMapping(path = "/teacher", method = RequestMethod.GET)
    public ModelAndView t1(String name, String password) {
        System.out.println(name);
        System.out.println(password);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("name", "张三");
        modelAndView.addObject("age", 30);
        modelAndView.setViewName("temp/view");
        return modelAndView;
    }

    // 与上面等价
    @RequestMapping(path = "/t2", method = RequestMethod.GET)
    public String t2(Model model, String name, String password) {
        System.out.println(name);
        System.out.println(password);
        model.addAttribute("name", "张三");
        model.addAttribute("age", 30);
        return "temp/view";
    }

    // ajax 字符串
    // java 对象 <==> json字符串  <==>js对象
    @RequestMapping(path = "/emp", method = RequestMethod.GET)
    public String getJson() {
        return "";
    }


    // cookie
    @RequestMapping(path = "/cookie", method = RequestMethod.GET)
    @ResponseBody
    public String c1(HttpServletResponse response) {

        Cookie code = new Cookie("code", "123");
        // 过期时间,如果不设置过期时间，那么当关闭浏览器时就会失效
        code.setMaxAge(10 * 60);
        // 只在该路径下有效
        code.setPath("/community/cookie");
        response.addCookie(code);
        return "";
    }

    //获得cookie
    @RequestMapping(path = "/cookie/get", method = RequestMethod.GET)
    @ResponseBody
    public String getCookie(@CookieValue("code") String code) {
        System.out.println(code);
        return "";
    }


    // https://github.dev/cosen1024/community
    //session
    //获得cookie
    @RequestMapping(path = "/session/set", method = RequestMethod.GET)
    @ResponseBody
    public String setCookie(HttpSession session) {
        session.setAttribute("id", "123");
        session.setAttribute("name", "xixi");
        return "123";
    }


    @RequestMapping(path = "/session/get", method = RequestMethod.GET)
    @ResponseBody
    public String getCookie(HttpSession session) {
        System.out.println(session.getAttribute("id"));
        System.out.println(session.getAttribute("name"));
        return "123";
    }

    // 错误
    @RequestMapping(path = "/error", method = RequestMethod.GET)
    public String  error() {

        try {
            int i = 1 / 0;
        } catch (Exception e) {
            logger.error("除0异常");
        }
        return "redirect:/error";
    }
}
