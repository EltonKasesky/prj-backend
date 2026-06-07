package com.prj.prjbackend.modules.auth.service;

import com.prj.prjbackend.infra.exception.auth.UserAuthNotFoundException;
import com.prj.prjbackend.modules.auth.dto.LoginRequestDTO;
import com.prj.prjbackend.modules.auth.dto.LoginResponseDTO;
import com.prj.prjbackend.modules.user.User;
import com.prj.prjbackend.modules.user.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public LoginResponseDTO authenticate(final LoginRequestDTO request) {
        User user = userRepository.findByEmail(request.email()).orElseThrow(
                () -> new UserAuthNotFoundException("Usuário ou senha incorretos.")
        );

        boolean passwordMatches = passwordEncoder.matches(request.password(), user.getPassword());

        if (!passwordMatches) {
            throw new BadCredentialsException("Usuário ou senha incorretos.");
        }

        return new LoginResponseDTO(tokenService.generateToken(user));
    }
}