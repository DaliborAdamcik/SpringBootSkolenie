package com.example.demo.ctrl;

import com.example.demo.model.User;
import com.example.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserRepository userRepo;

    @GetMapping()
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User saveUser(@RequestBody User user) {
        if (user.getId() == null)
            return userRepo.save(user);

        throw new UnsupportedOperationException("Do not use save to make updates");
    }

}
