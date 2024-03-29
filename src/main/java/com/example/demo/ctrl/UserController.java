package com.example.demo.ctrl;

import com.example.demo.exc.InvalidNameOrPasswordException;
import com.example.demo.exc.UserNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController()
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserRepository userRepo;

    @GetMapping()
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        Optional<User> ousr = userRepo.findById(id);
        if (ousr.isPresent())
            return ousr.get();

        throw new UserNotFoundException(String.format("User with id %s not found", id));
    }

    @GetMapping("/raw/{id}")
    public Optional<User> getUserByIdRaw(@PathVariable("id") Long id) {
        return userRepo.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User saveUser(@RequestBody User user) {
        if (user.getId() == null)
            return userRepo.save(user);

        throw new UnsupportedOperationException("Do not use save to make updates");
    }

    @PostMapping("/login/{userName}")
    public User verifypassword(@PathVariable("userName") String userName,
                               @RequestBody Map<String, String> params) {

        Optional<User> user = userRepo.findByName(userName);
        if (user.isPresent()) {
            User usr = user.get();
            if (usr.isPasswordValid(params.get("pass")))
                return usr;
        }

        throw new InvalidNameOrPasswordException("Invalid name or password");


    }

}
