package com.prj.prjbackend.modules.usersticker.mapper;

import com.prj.prjbackend.modules.user.User;
import com.prj.prjbackend.modules.user.dto.UserRegisterRequestDTO;
import com.prj.prjbackend.modules.user.dto.UserResponseDTO;
import com.prj.prjbackend.modules.usersticker.UserSticker;
import com.prj.prjbackend.modules.usersticker.dto.UserStickerResponseDTO;

public interface IUserStickerMapper {
    UserStickerResponseDTO toDTO(UserSticker entity);
}
