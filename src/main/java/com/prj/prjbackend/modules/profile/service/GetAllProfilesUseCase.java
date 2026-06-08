package com.prj.prjbackend.modules.profile.service;

import com.prj.prjbackend.modules.profile.dto.ProfileResponseDTO;
import com.prj.prjbackend.modules.profile.mapper.IProfileMapper;
import com.prj.prjbackend.modules.profile.repository.IProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAllProfilesUseCase {
    private final IProfileRepository profileRepository;
    private final IProfileMapper profileMapper;

    public Page<ProfileResponseDTO> execute(final int page, final int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());

        return profileRepository.findAll(pageable).map(profileMapper::toDTO);
    }
}
