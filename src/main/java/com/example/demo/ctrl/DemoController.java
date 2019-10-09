package com.example.demo.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController()
@RequestMapping("/api")
public class DemoController {
    @Autowired
    private JdbcTemplate jdbc;

    @GetMapping("/")
    public String apiVer() {
        return "0.1";
    }
}
