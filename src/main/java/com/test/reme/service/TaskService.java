package com.test.reme.service;

import com.test.reme.entity.Task;
import com.test.reme.entity.User;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.List;
import java.util.UUID;

public interface TaskService {
     // my way
    //  Task createTask (User user, UUID uuid, Task task, JwtAuthenticationToken token);
    Task createTask (Task task , JwtAuthenticationToken token);
    void delete (Long id , JwtAuthenticationToken token);
    Task updateTask(Task task ,UUID uuid , JwtAuthenticationToken token);
    Task getTask(Long id , JwtAuthenticationToken token);
    List<Task> getTasks(JwtAuthenticationToken token);
}
