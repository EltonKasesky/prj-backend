package com.prj.prjbackend.modules.usersticker.mapper;

import com.prj.prjbackend.modules.usersticker.UserSticker;
import com.prj.prjbackend.modules.usersticker.dto.UserStickerResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class UserStickerMapper implements IUserStickerMapper{
    public UserStickerResponseDTO toDTO(UserSticker entity) {
        return new UserStickerResponseDTO(
                entity.getId(),
                entity.getSticker().getTag(),
                entity.getSticker().getName(),
                entity.getQuantity()
        );
    }
}