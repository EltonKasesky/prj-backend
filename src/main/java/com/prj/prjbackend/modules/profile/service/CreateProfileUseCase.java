package com.prj.prjbackend.modules.profile.service;

import com.prj.prjbackend.infra.exception.profile.ProfileAlreadyExists;
import com.prj.prjbackend.modules.profile.Profile;
import com.prj.prjbackend.modules.profile.dto.ProfileRegisterRequestDTO;
import com.prj.prjbackend.modules.profile.mapper.IProfileMapper;
import com.prj.prjbackend.modules.profile.repository.IProfileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateProfileUseCase {
    private final IProfileRepository profileRepository;
    private final IProfileMapper profileMapper;

    @Transactional
    public void execute(final ProfileRegisterRequestDTO request){
        profileRepository.findByName(request.name()).ifPresent(profile -> {
            throw new ProfileAlreadyExists("Esse perfil já existe.");
        });

        Profile profile = profileMapper.toEntity(request);
        profileRepository.save(profile);
    }
}
