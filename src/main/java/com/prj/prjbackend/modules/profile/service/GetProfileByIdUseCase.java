package com.prj.prjbackend.modules.profile.service;

import com.prj.prjbackend.infra.exception.profile.ProfileNotFoundException;
import com.prj.prjbackend.modules.profile.Profile;
import com.prj.prjbackend.modules.profile.dto.ProfileResponseDTO;
import com.prj.prjbackend.modules.profile.mapper.IProfileMapper;
import com.prj.prjbackend.modules.profile.repository.IProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetProfileByIdUseCase {
    private final IProfileRepository profileRepository;
    private final IProfileMapper profileMapper;

    public ProfileResponseDTO execute(final UUID id){
        Profile profile = profileRepository.findById(id).orElseThrow(
                () -> new ProfileNotFoundException("O perfil não pode ser encontrado.")
        );

        return profileMapper.toDTO(profile);
    }
}
