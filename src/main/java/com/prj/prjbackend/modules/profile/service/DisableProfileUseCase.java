package com.prj.prjbackend.modules.profile.service;

import com.prj.prjbackend.infra.exception.profile.ProfileDisabledException;
import com.prj.prjbackend.infra.exception.profile.ProfileNotFoundException;
import com.prj.prjbackend.modules.profile.Profile;
import com.prj.prjbackend.modules.profile.repository.IProfileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DisableProfileUseCase {
    private IProfileRepository profileRepository;

    @Transactional
    public void execute(final UUID id){
        Profile profile = profileRepository.findById(id).orElseThrow(
                () -> new ProfileNotFoundException("O perfil não pode ser encontrado.")
        );

        if (profile.getStatus().equals(false))
            throw new ProfileDisabledException("O perfil já está desativado.");

        profile.setStatus(false);
        profileRepository.save(profile);
    }
}
