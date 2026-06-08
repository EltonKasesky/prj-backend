package com.prj.prjbackend.modules.user.service;

import com.prj.prjbackend.infra.exception.user.UserDisabledException;
import com.prj.prjbackend.infra.exception.user.UserNotFoundException;
import com.prj.prjbackend.modules.user.User;
import com.prj.prjbackend.modules.user.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ActiveUserUseCase {
    private final IUserRepository userRepository;

    public void execute(final UUID id){
        User user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("O usuário não pode ser encontrado.")
        );

        if (user.getStatus().equals(true))
            throw new UserDisabledException("O usuário já está ativo.");

        user.setStatus(true);
        userRepository.save(user);
    }
}
