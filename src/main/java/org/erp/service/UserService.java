package org.erp.service;

import lombok.RequiredArgsConstructor;
import org.erp.exception.UserAlreadyExistException;
import org.erp.model.dto.LoginDTO;
import org.erp.model.user.UserEntity;
import org.erp.repository.RoleRepository;
import org.erp.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.security.provider.certpath.OCSPResponse;

import java.util.Arrays;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserEntity registerNewUserAccount(final LoginDTO accountDto) {
        if (emailExists(accountDto.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email adress: " + accountDto.getEmail());
        }

        userRepository.save(UserEntity.builder()
        .email(accountDto.getEmail())
        .name(accountDto.getUsername())
        .password(passwordEncoder.encode(accountDto.getPassword()))
        .roleEntities(Arrays.asList(roleRepository.findByName("ROLE_USER")))
        .build());

        return null;
    }

    private boolean emailExists(final String email) {
        return userRepository.findByEmail(email) != null;
    }
}
