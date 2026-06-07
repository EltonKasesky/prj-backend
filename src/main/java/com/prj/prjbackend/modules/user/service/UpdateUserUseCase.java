package com.prj.prjbackend.modules.user.service;

import com.prj.prjbackend.infra.exception.user.UserAlreadyExistsException;
import com.prj.prjbackend.infra.exception.user.UserDisabledException;
import com.prj.prjbackend.infra.exception.user.UserNotFoundException;
import com.prj.prjbackend.modules.user.User;
import com.prj.prjbackend.modules.user.dto.UserUpdateRequestDTO;
import com.prj.prjbackend.modules.user.repository.IUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateUserUseCase {
    private final IUserRepository userRepository;

    @Transactional
    public void execute(final UserUpdateRequestDTO request){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("O usuário não pode ser encontrado.")
        );

        userRepository.findByEmail(request.email()).ifPresent(emailUser -> {
            throw new UserAlreadyExistsException("A troca de email é invalida. Pois o email usado pertence a outro usuário.");
        });

        if (user.getStatus().equals(false))
            throw new UserDisabledException("O usuário está desabilitado.");

        user.setName(request.name());
        user.setEmail(request.email());

        userRepository.save(user);
    }
}
