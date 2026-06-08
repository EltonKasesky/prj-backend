package com.prj.prjbackend.modules.profile.service;

import com.prj.prjbackend.infra.exception.profile.ProfileNotFoundException;
import com.prj.prjbackend.infra.exception.user.UserNotFoundException;
import com.prj.prjbackend.modules.profile.Profile;
import com.prj.prjbackend.modules.profile.dto.ProfileResponseDTO;
import com.prj.prjbackend.modules.profile.mapper.IProfileMapper;
import com.prj.prjbackend.modules.profile.repository.IProfileRepository;
import com.prj.prjbackend.modules.user.User;
import com.prj.prjbackend.modules.user.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetProfilesFromUserIdUseCase {
    private final IUserRepository userRepository;
    private final IProfileRepository profileRepository;
    private final IProfileMapper profileMapper;

    public List<ProfileResponseDTO> execute(final UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("O usuário não pode ser encontrado.")
        );

        List<Profile> profiles = new LinkedList<>();
        user.getProfiles().forEach(profile -> {
            profiles.add(profileRepository.findById(profile.getId()).orElseThrow(
                    () -> new ProfileNotFoundException("O perfil não pode ser encontrado.")
            ));
        });

        List<ProfileResponseDTO> response = new LinkedList<>();
        profiles.forEach(profile -> {
            response.add(profileMapper.toDTO(profile));
        });

        return response;
    }
}
