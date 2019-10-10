package com.example.demo.ctrl;

import com.example.demo.model.Task;
import com.example.demo.repo.TaskRepository;
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
    public Task addTask(@RequestBody Task task) {
        return taskRepo.save(task);
    }

}
