package com.example.demo.ctrl;

import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.repo.TaskRepository;
import com.example.demo.resolver.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {
    @Autowired
    private TaskRepository taskRepo;

    @GetMapping
    public List<Task> getAllTask() {
        return taskRepo.findAll();
    }

    @PostMapping
    public Task addTask(@RequestBody Task task, @CurrentUser User user) {
        System.out.println("Current user is " + user);
        return taskRepo.save(task);
    }

}
