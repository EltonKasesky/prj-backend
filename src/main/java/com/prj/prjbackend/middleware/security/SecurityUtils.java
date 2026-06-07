package com.prj.prjbackend.middleware.security;

import com.prj.prjbackend.modules.user.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("securityUtils")
@RequiredArgsConstructor
public class SecurityUtils {
    private final IUserRepository userRepository;

    public boolean isValidAdmin() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepository.findByEmail(email)
                .map(user -> user.getStatus() && user.getProfiles().stream()
                        .anyMatch(p -> p.getName().equals("ROLE_ADMIN")))
                .orElse(false);
    }
}