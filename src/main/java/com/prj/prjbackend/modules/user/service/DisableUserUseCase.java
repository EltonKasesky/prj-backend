package com.prj.prjbackend.modules.user.service;

import com.prj.prjbackend.infra.user.UserDisabledException;
import com.prj.prjbackend.infra.user.UserNotFoundException;
import com.prj.prjbackend.modules.user.User;
import com.prj.prjbackend.modules.user.repository.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DisableUserUseCase {
    private final IUserRepository userRepository;

    public DisableUserUseCase(IUserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void execute(final UUID id){
        User user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("O usuário não pode ser encontrado.")
        );

        if (user.getStatus().equals(false))
            throw new UserDisabledException("O usuário já está desativado.");

        user.setStatus(false);
        userRepository.save(user);
    }
}
