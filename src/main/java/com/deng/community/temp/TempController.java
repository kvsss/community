package com.deng.community.temp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author :deng
 * @version :1.0
 * @since :1.8
 */

@Controller
@RequestMapping("/temp")
public class TempController {

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

}
