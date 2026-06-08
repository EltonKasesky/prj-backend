package com.prj.prjbackend.modules.profile.mapper;

import com.prj.prjbackend.modules.profile.Profile;
import com.prj.prjbackend.modules.profile.dto.ProfileRegisterRequestDTO;
import com.prj.prjbackend.modules.profile.dto.ProfileResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class ProfileMapper implements IProfileMapper{
    @Override
    public ProfileResponseDTO toDTO(Profile profile) {
        return new ProfileResponseDTO(
                profile.getId(),
                profile.getName()
        );
    }

    @Override
    public Profile toEntity(ProfileRegisterRequestDTO request){
        Profile profile = new Profile();
        profile.setName(request.name());
        return profile;
    }
}
