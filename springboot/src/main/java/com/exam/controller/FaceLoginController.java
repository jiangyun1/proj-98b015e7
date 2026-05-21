package com.exam.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin
public class FaceLoginController {
    @RequestMapping(value = "toLogin")
    public String toLogin() {
        return "faceLogin";
    }
    @RequestMapping(value = "toRegister")
    public String totoRegister() {
        return "faceRegister";
    }

    @RequestMapping(value = "toErro")
    public String toErro() {

        return "erro";
    }

    @RequestMapping(value = "toSuccess")
    public String toSuccess() {

        return "success";
    }
}
