package com.prj.prjbackend.modules.profile.service;

import com.prj.prjbackend.infra.exception.profile.ProfileAlreadyExists;
import com.prj.prjbackend.infra.exception.profile.ProfileNotFoundException;
import com.prj.prjbackend.modules.profile.Profile;
import com.prj.prjbackend.modules.profile.dto.ProfileUpdateRequestDTO;
import com.prj.prjbackend.modules.profile.repository.IProfileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateProfileUseCase {
    private final IProfileRepository profileRepository;

    @Transactional
    public void execute(final UUID id, final ProfileUpdateRequestDTO request){
        Profile profile = profileRepository.findById(id).orElseThrow(
                () -> new ProfileNotFoundException("O perfil não pode ser encontrado.")
        );

        profileRepository.findByName(request.name()).ifPresent(name -> {
            throw new ProfileAlreadyExists("Esse perfil já existe.");
        });

        profile.setName(request.name());
        profileRepository.save(profile);
    }
}
