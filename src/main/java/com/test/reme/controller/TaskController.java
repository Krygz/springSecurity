package com.test.reme.controller;

import com.test.reme.entity.Task;
import com.test.reme.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    // my way
//    @PostMapping("/newTask")
//    public ResponseEntity<Task> newTask (@RequestBody User user, @PathVariable UUID uuid,
//                         @RequestBody Task task, JwtAuthenticationToken token){
//        var newTask = taskService.createTask(user,uuid,task,token);
//        return ResponseEntity.status(HttpStatus.CREATED).body(newTask);
//    }

    @PostMapping("/newTask")
    public ResponseEntity<Task> newTask(@RequestBody Task task , JwtAuthenticationToken token){
        var newTask = taskService.createTask(task , token);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id , JwtAuthenticationToken token){
        taskService.delete(id,token);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@RequestBody Task task ,@PathVariable UUID uuid , JwtAuthenticationToken token){
        var updatedTask = taskService.updateTask(task,uuid,token);
        return ResponseEntity.status(HttpStatus.OK).body(updatedTask);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getById(@PathVariable Long id  , JwtAuthenticationToken token){
        var getTask = taskService.getTask(id,token);
        return ResponseEntity.status(HttpStatus.OK).body(getTask);
    }
}
