package com.prj.prjbackend.middleware.security;

import com.prj.prjbackend.infra.exception.user.UserNotFoundException;
import com.prj.prjbackend.modules.user.User;
import com.prj.prjbackend.modules.user.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("securityUtils")
@RequiredArgsConstructor
public class SecurityUtils {
    private final IUserRepository userRepository;

    public boolean isValidAdmin() {
        return hasActiveProfile("ROLE_ADMIN");
    }

    public boolean isValidAuthor() {
        return hasActiveProfile("ROLE_AUTHOR");
    }

    public boolean isValidCollector() {
        return hasActiveProfile("ROLE_COLLECTOR");
    }

    private boolean hasActiveProfile(String profileName) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepository.findByEmail(email)
                .map(user -> Boolean.TRUE.equals(user.getStatus()) && user.getProfiles().stream()
                        .anyMatch(p -> p.getName().equals(profileName)))
                .orElse(false);
    }

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UserNotFoundException("Usuário não autenticado.");
        }

        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("Usuário autenticado não encontrado."));
    }
}