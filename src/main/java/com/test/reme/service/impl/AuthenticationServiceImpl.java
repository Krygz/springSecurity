package com.test.reme.service.impl;

import com.test.reme.dto.LoginRequest;
import com.test.reme.dto.LoginResponse;
import com.test.reme.dto.RegisterRequest;
import com.test.reme.dto.UserResponse;
import com.test.reme.entity.Role;
import com.test.reme.entity.User;
import com.test.reme.repository.RoleRepository;
import com.test.reme.repository.UserRepository;
import com.test.reme.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepository;
    private final RoleRepository role;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {

        var user = userRepository.findByEmail(loginRequest.email());

        if (user.isEmpty() || !user.get().isLoginCorrect(loginRequest, passwordEncoder)) {
            throw new BadCredentialsException("user or password is invalid!");
        }

        var now = Instant.now();
        var expiresIn = 300L;

        var scopes = user.get().getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .issuer("mybackend")
                .subject(user.get().getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scope", scopes)
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return new LoginResponse(jwtValue, expiresIn);
    }

    @Transactional
    @Override
    public UserResponse register(RegisterRequest request) {
        var userFromDb = userRepository.findByEmail(request.email());
        if (userFromDb.isPresent() || userRepository.existsByEmail(request.email())){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        User newUser = new User();
        setUser(newUser,request);
        userRepository.save(newUser);
        return new UserResponse(newUser.getId(),newUser.getName(),newUser.getEmail());
    }

    public void setUser(User user ,RegisterRequest request){
        var commomRole = role.findByName(Role.Values.COMMON.name());
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRoles(Set.of(commomRole));
    }

}
