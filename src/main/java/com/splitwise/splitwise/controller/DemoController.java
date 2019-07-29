package com.splitwise.splitwise.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/demo")
@CrossOrigin
public class DemoController {

    @GetMapping
    public String getDemo() {
        return "Demo";
    }
}
