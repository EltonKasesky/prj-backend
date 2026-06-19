package com.prj.prjbackend.modules.user.service;

import com.prj.prjbackend.infra.exception.user.InvalidUserPasswordException;
import com.prj.prjbackend.infra.exception.user.NotEqualsPasswordException;
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

        if (!passwordEncoder.matches(request.currentPassword(), user.getPassword()))
            throw new InvalidUserPasswordException("A senha mencionada está incorreta ou é invalida.");

        if (!request.newPassword().equals(request.confirmPassword()))
            throw new NotEqualsPasswordException("As senhas devem ser iguais para a troca da senha.");

        user.setPassword(passwordEncoder.encode(request.newPassword()));

        userRepository.save(user);
    }
}
