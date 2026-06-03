package com.prj.prjbackend.modules.user.mapper;

import com.prj.prjbackend.modules.user.User;
import com.prj.prjbackend.modules.user.dto.UserResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements IUserMapper {
    public UserResponseDTO toDTO(User user){
        return new UserResponseDTO(
            user.getId(),
            user.getName(),
            user.getEmail()
        );
    }
}
