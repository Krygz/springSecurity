package com.test.reme.controller;

import com.test.reme.entity.User;
import com.test.reme.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/getAllUsers")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public ResponseEntity<List<User>> getAllUsers (){
        List<User> list = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @GetMapping("/{email}")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public ResponseEntity <User>getByEmail(@PathVariable @Valid String email){
       User user = userService.getByEmail(email);
       return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @DeleteMapping("/{uuid}")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public ResponseEntity<Void> deleteByUUID(@PathVariable @Valid UUID uuid , JwtAuthenticationToken token){
        userService.deleteByUUID(uuid , token);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<User> updateAccount (@RequestBody @Valid User user , @PathVariable UUID uuid , JwtAuthenticationToken token){
        var userUpdated = userService.updateAccount(user, uuid, token);
        return ResponseEntity.status(HttpStatus.OK).body(userUpdated);
    }
}
