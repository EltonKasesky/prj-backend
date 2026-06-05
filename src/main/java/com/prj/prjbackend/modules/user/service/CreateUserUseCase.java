package com.prj.prjbackend.modules.user.service;

import com.prj.prjbackend.infra.user.UserAlreadyExistsException;
import com.prj.prjbackend.modules.user.User;
import com.prj.prjbackend.modules.user.dto.UserRegisterRequestDTO;
import com.prj.prjbackend.modules.user.repository.IUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateUserUseCase {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateUserUseCase(IUserRepository userRepository,
                             PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void execute(final UserRegisterRequestDTO request){
        userRepository.findByEmail(request.email()).ifPresent(user -> {
            throw new UserAlreadyExistsException("Esse email já pertence a um usuário.");
        });

        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setStatus(true);

        userRepository.save(user);
    }
}
