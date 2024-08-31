//generate admin
//package com.test.reme.config;
//
//import com.test.reme.entity.Role;
//import com.test.reme.entity.User;
//import com.test.reme.repository.RoleRepository;
//import com.test.reme.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Set;
//
//@Configuration
//@RequiredArgsConstructor
//public class AdminConfig {
//
//    private final UserRepository userRepository;
//    private final RoleRepository roleRepository;
//    private final BCryptPasswordEncoder passwordEncoder;
//
//    @Transactional
//    @Bean
//    public User createAdmin() {
//        User createdAdmin = new User();
//        var adminRole = roleRepository.findByName(Role.Values.ADMIN.name());
//        createdAdmin.setName("admin");
//        createdAdmin.setEmail("admin@gmail.com");
//        createdAdmin.setPassword(passwordEncoder.encode("adminzeca1"));
//        createdAdmin.setRoles(Set.of(adminRole));
//        return userRepository.save(createdAdmin);
//    }
//}
