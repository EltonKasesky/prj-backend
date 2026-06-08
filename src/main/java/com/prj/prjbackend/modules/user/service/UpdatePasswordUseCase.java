package com.prj.prjbackend.modules.user.service;

import com.prj.prjbackend.infra.exception.user.UserNotFoundException;
import com.prj.prjbackend.modules.user.User;
import com.prj.prjbackend.modules.user.dto.UserPasswordRequestDTO;
import com.prj.prjbackend.modules.user.repository.IUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdatePasswordUseCase {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void execute(final UserPasswordRequestDTO request){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("O usuário não pode ser encontrado.")
        );

        user.setPassword(passwordEncoder.encode(request.password()));
        userRepository.save(user);
    }
}
