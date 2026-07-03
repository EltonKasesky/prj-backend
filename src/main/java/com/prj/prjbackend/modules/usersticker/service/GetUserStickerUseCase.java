package com.prj.prjbackend.modules.usersticker.service;

import com.prj.prjbackend.infra.exception.usersticker.UserStickerNotFoundException;
import com.prj.prjbackend.middleware.security.SecurityUtils;
import com.prj.prjbackend.modules.user.User;
import com.prj.prjbackend.modules.usersticker.UserSticker;
import com.prj.prjbackend.modules.usersticker.dto.UserStickerResponseDTO;
import com.prj.prjbackend.modules.usersticker.mapper.UserStickerMapper;
import com.prj.prjbackend.modules.usersticker.repository.IUserStickerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetUserStickerUseCase {

    private final IUserStickerRepository userStickerRepository;
    private final SecurityUtils securityUtils;
    private final UserStickerMapper mapper;

    public UserStickerResponseDTO execute(UUID id) {
        User authenticatedUser = securityUtils.getAuthenticatedUser();

        UserSticker userSticker = userStickerRepository
                .findByIdAndUserId(id, authenticatedUser.getId())
                .orElseThrow(() ->
                        new UserStickerNotFoundException(
                                "Figurinha não encontrada na coleção."
                        ));

        return mapper.toDTO(userSticker);
    }
}
