package com.prj.prjbackend.modules.user.service;

import com.prj.prjbackend.infra.exception.user.UserDisabledException;
import com.prj.prjbackend.infra.exception.user.UserNotFoundException;
import com.prj.prjbackend.modules.user.User;
import com.prj.prjbackend.modules.user.repository.IUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DisableAuthenticatedUserUseCase {
    private final IUserRepository userRepository;

    @Transactional
    public void execute(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("O usuário não pode ser encontrado.")
        );

        if (user.getStatus().equals(false))
            throw new UserDisabledException("O usuário já está desativado.");

        user.setStatus(false);
        userRepository.save(user);
    }
}
