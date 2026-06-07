package com.prj.prjbackend.modules.user.service;

import com.prj.prjbackend.infra.exception.user.UserDisabledException;
import com.prj.prjbackend.infra.exception.user.UserNotFoundException;
import com.prj.prjbackend.modules.user.User;
import com.prj.prjbackend.modules.user.dto.UserResponseDTO;
import com.prj.prjbackend.modules.user.mapper.IUserMapper;
import com.prj.prjbackend.modules.user.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAuthenticatedUserUseCase {
    private final IUserRepository userRepository;
    private final IUserMapper userMapper;

    public UserResponseDTO execute() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("O usuário não pode ser encontrado.")
        );

        if (user.getStatus().equals(false)) {
            throw new UserDisabledException("O usuário está desabilitado.");
        }

        return userMapper.toDTO(user);
    }
}