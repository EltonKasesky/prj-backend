package com.prj.prjbackend.modules.user.service;

import com.prj.prjbackend.infra.exception.user.UserDisabledException;
import com.prj.prjbackend.infra.exception.user.UserNotFoundException;
import com.prj.prjbackend.modules.user.User;
import com.prj.prjbackend.modules.user.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DisableUserByIdUseCase {
    private final IUserRepository userRepository;

    public void execute(final UUID userId){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("O usuário não pode ser encontrado.")
        );

        if (user.getStatus().equals(false))
            throw new UserDisabledException("O usuário já está desabilitado.");

        user.setStatus(false);
        userRepository.save(user);
    }
}
