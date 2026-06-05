package com.prj.prjbackend.modules.user.service;

import com.prj.prjbackend.infra.user.UserAlreadyExistsException;
import com.prj.prjbackend.infra.user.UserDisabledException;
import com.prj.prjbackend.infra.user.UserNotFoundException;
import com.prj.prjbackend.modules.user.User;
import com.prj.prjbackend.modules.user.dto.UserUpdateRequestDTO;
import com.prj.prjbackend.modules.user.repository.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateUserUseCase {
    private final IUserRepository userRepository;

    public UpdateUserUseCase(IUserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void execute(final UUID id, final UserUpdateRequestDTO request){
        User user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("O usuário não pode ser encontrado.")
        );

        userRepository.findByEmail(request.email()).ifPresent(email -> {
            throw new UserAlreadyExistsException("A troca de email é invalida. Pois o email usado pertence a outro usuário.");
        });

        if (user.getStatus().equals(false))
            throw new UserDisabledException("O usuário está desabilitado.");

        user.setName(request.name());
        user.setEmail(request.email());

        userRepository.save(user);
    }
}
