package com.prj.prjbackend.modules.profile.mapper;

import com.prj.prjbackend.modules.profile.Profile;
import com.prj.prjbackend.modules.profile.dto.ProfileRegisterRequestDTO;
import com.prj.prjbackend.modules.profile.dto.ProfileResponseDTO;

public interface IProfileMapper {
    ProfileResponseDTO toDTO(Profile profile);
    Profile toEntity(ProfileRegisterRequestDTO request);
}
