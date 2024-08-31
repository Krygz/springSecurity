package com.test.reme.service.impl;

import com.test.reme.entity.Role;
import com.test.reme.entity.Task;
import com.test.reme.repository.TaskRepository;
import com.test.reme.repository.UserRepository;
import com.test.reme.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Task createTask(Task task, JwtAuthenticationToken token) {
        var user = userRepository.findById(UUID.fromString(token.getName()));
        var newTask = new Task();

        newTask.setUser(user.get());
        newTask.setTitle(task.getTitle());
        newTask.setDescription(task.getDescription());
        taskRepository.save(newTask);
        return new Task(newTask.getId(), newTask.getUser(), newTask.getTitle(), newTask.getDescription(), newTask.getCreatedAt());
    }


    @Override
    @Transactional
    public void delete(Long id, JwtAuthenticationToken token) {
        {
            var user = userRepository.findById(UUID.fromString(token.getName()));
            var task = taskRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("id not found"));

            var isAdmin = user.get().getRoles()
                    .stream()
                    .anyMatch(role -> role.getName().equalsIgnoreCase(Role.Values.ADMIN.name()));

            if (isAdmin || task.getUser().getId().equals(UUID.fromString(token.getName()))) {
                taskRepository.deleteById(id);
            } else {
                throw new RuntimeException("you are unauthorized");
            }
        }
    }

    @Override
    @Transactional
    public Task updateTask(Task task, UUID uuid, JwtAuthenticationToken token) {
        String tokenName = token.getName();
        UUID tokenUUID;
        try {
            tokenUUID = UUID.fromString(tokenName);
        }catch (IllegalArgumentException exception){
            throw new RuntimeException("invalid UUID in the token" + token,exception);
        }
        userRepository.findById(tokenUUID)
                .orElseThrow(() -> new RuntimeException("UIID not found"));

        if (!uuid.equals(tokenUUID)) {
            throw new RuntimeException("Unauthorized user for this id");
        }

        var existingTask = taskRepository.findById(task.getId())
                .orElseThrow(() -> new RuntimeException("task id not found"));

        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        taskRepository.save(existingTask);
        return existingTask;
    }

    @Override
    @Transactional(readOnly = true)
    public Task getTask(Long id, JwtAuthenticationToken token) {
        var userUuid = userRepository.findById(UUID.fromString(token.getName()));
        var isAdmin = userUuid.get().getRoles()
                .stream()
                .anyMatch(role -> role.getName().equalsIgnoreCase(Role.Values.ADMIN.name()));
        if (!isAdmin || !userUuid.equals(UUID.fromString(token.getName()))){
            throw new RuntimeException("unauthorized");
        }
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("id not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> getTasks(JwtAuthenticationToken token) {
        var userUuid = userRepository.findById(UUID.fromString(token.getName()));
        var isAdmin = userUuid.get().getRoles()
                .stream()
                .anyMatch(role -> role.getName().equalsIgnoreCase(Role.Values.ADMIN.name()));
        if (!isAdmin || !userUuid.equals(UUID.fromString(token.getName()))){
            throw new RuntimeException("unauthorized");
        }
        return taskRepository.findAll();
    }
}

// my way
//    @Override
//    @Transactional
//    public Task createTask(User user, UUID uuid, Task task, JwtAuthenticationToken token) {
//        //extract token name
//        String tokenName = token.getName();
//        //extract the uuid  i guess????
//        UUID tokenUuid;
//
//        try{
//        //try convert token name to uuid
//        tokenUuid = UUID.fromString(tokenName);
//        }catch (IllegalArgumentException exception){
//            throw new RuntimeException("UUID invalid in token"+tokenName , exception);
//        }
//         // try to find the user by uuid in the database
//         userRepository.findById(tokenUuid)
//                .orElseThrow(() -> new RuntimeException("user not found"));
//        //confirm if the user uuid matches with auth user
//        if (!uuid.equals(tokenUuid)){
//            throw new RuntimeException("unauthorized");
//        }
//        Task newTask = new Task();
//        newTask.setName(task.getName());
//        newTask.setUser(user);
//        newTask.setDescription(task.getDescription());
//        newTask.setCreatedAt(Instant.now());
//
//        taskRepository.save(newTask);
//
//        return newTask;
//    }
