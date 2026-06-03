package com.prj.prjbackend.modules.user.mapper;

import com.prj.prjbackend.modules.user.User;
import com.prj.prjbackend.modules.user.dto.UserResponseDTO;

public interface IUserMapper {
    UserResponseDTO toDTO(User user);
}
