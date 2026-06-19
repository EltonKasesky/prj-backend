package com.prj.prjbackend.modules.profile.service;

import com.prj.prjbackend.infra.exception.profile.ProfileDisabledException;
import com.prj.prjbackend.infra.exception.profile.ProfileNotFoundException;
import com.prj.prjbackend.infra.exception.profile.UserAlreadyHaveProfileException;
import com.prj.prjbackend.infra.exception.user.UserDisabledException;
import com.prj.prjbackend.infra.exception.user.UserNotFoundException;
import com.prj.prjbackend.modules.profile.Profile;
import com.prj.prjbackend.modules.profile.dto.ProfileToUserRequestDTO;
import com.prj.prjbackend.modules.profile.repository.IProfileRepository;
import com.prj.prjbackend.modules.user.User;
import com.prj.prjbackend.modules.user.repository.IUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RemoveProfileUserUseCase {
    private final IUserRepository userRepository;
    private final IProfileRepository profileRepository;

    @Transactional
    public void execute(final UUID userId, ProfileToUserRequestDTO request) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("O usuário não pode ser encontrado.")
        );

        if (user.getStatus().equals(false))
            throw new UserDisabledException("O usuário está desativado.");

        List<Profile> profiles = new ArrayList<>();
        request.profileNames().forEach(profile -> {
            profiles.add(profileRepository.findByName(profile).orElseThrow(
                    () -> new ProfileNotFoundException("O perfil não pode ser encontrado.")
            ));
        });

        if (profiles.stream().anyMatch(profile -> !profile.getStatus()))
            throw new ProfileDisabledException("Um ou mais perfis estão desativados.");

        List<Profile> existingProfiles = profiles.stream()
                .filter(user.getProfiles()::contains)
                .toList();

        if (existingProfiles.isEmpty())
            throw new UserAlreadyHaveProfileException("O usuário não possui um ou mais dos perfis solicitados.");

        existingProfiles.forEach(user.getProfiles()::remove);
        userRepository.save(user);
    }
}
