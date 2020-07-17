package com.wzh.web.controller;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author: wzh
 * @Date: 2020/7/7 9:51
 **/
@Controller
@RequestMapping(value = "/test")
public class TestController {

    @RequestMapping("/1")
    @ResponseBody
    public String test(){
        return new JSONObject("{\"id\":233}").toString();
    }

    @RequestMapping("/2")
    public ModelAndView test0934(){
        ModelAndView modelAndView = new ModelAndView("/list");
        modelAndView.addObject("name","66666666666666666666诺安成长混合\t");
        return modelAndView;
    }
}
