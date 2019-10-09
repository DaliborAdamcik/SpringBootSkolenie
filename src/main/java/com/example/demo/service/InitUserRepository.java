package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
class InitUserRepository {
    @Autowired
    private UserRepository repo;

    @PostConstruct
    private void init() {
        if (repo.count() == 0) {
            repo.save(new User("admin", "admin"));
        }
    }
}
