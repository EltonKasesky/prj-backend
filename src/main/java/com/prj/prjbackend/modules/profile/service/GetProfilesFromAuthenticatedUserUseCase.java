package com.prj.prjbackend.modules.profile.service;

import com.prj.prjbackend.infra.exception.user.UserDisabledException;
import com.prj.prjbackend.infra.exception.user.UserNotFoundException;
import com.prj.prjbackend.modules.profile.dto.ProfileResponseDTO;
import com.prj.prjbackend.modules.profile.repository.IProfileRepository;
import com.prj.prjbackend.modules.user.User;
import com.prj.prjbackend.modules.user.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetProfilesFromAuthenticatedUserUseCase {
    private final IProfileRepository profileRepository;
    private final IUserRepository userRepository;

    public List<ProfileResponseDTO> execute(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("O usuário não pode ser encontrado.")
        );

        if (user.getStatus().equals(false)) {
            throw new UserDisabledException("O usuário está desabilitado.");
        }

        List<ProfileResponseDTO> profiles = new ArrayList<>();
        user.getProfiles().forEach(profile -> {
            profiles.add(new ProfileResponseDTO(profile.getId(), profile.getName()));
        });

        return profiles;
    }
}
