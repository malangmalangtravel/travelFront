package com.pr.member.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VueController {

    @RequestMapping("/api/testVue")
    public String testVue() {
        return "testVue!!!";
    }
}
