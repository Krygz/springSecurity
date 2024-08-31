package com.test.reme.service.impl;

import com.test.reme.entity.User;
import com.test.reme.repository.UserRepository;
import com.test.reme.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("email not found"));
    }

    @Override
    @Transactional
    public void deleteByUUID(UUID uuid ,JwtAuthenticationToken token) {
      var userFromDb = userRepository.findById(uuid);
      if (userFromDb.isEmpty()){
        throw  new RuntimeException("id not exists");
      }
      userRepository.deleteById(uuid);
    }
    @Override
    @Transactional
    public User updateAccount(User user, UUID uuid, JwtAuthenticationToken token) {
        String tokenName = token.getName();
        UUID tokenUuid;

        try {
            tokenUuid = UUID.fromString(tokenName);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("UUID inválido no token: " + tokenName, e);
        }

        User tokenUser = userRepository.findById(tokenUuid)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado para o UUID do token"));

        if (!uuid.equals(tokenUuid)) {
            throw new RuntimeException("Usuário não autorizado a atualizar este ID");
        }

        User existingUser = userRepository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("ID de usuário não encontrado"));

        existingUser.setName(user.getName());

        userRepository.save(existingUser);

        return existingUser;
    }
}