package com.prj.prjbackend.modules.usersticker.service;

import com.prj.prjbackend.middleware.security.SecurityUtils;
import com.prj.prjbackend.modules.user.User;
import com.prj.prjbackend.modules.usersticker.dto.UserStickerResponseDTO;
import com.prj.prjbackend.modules.usersticker.mapper.UserStickerMapper;
import com.prj.prjbackend.modules.usersticker.repository.IUserStickerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListUserStickerUseCase {

    private final IUserStickerRepository userStickerRepository;
    private final SecurityUtils securityUtils;
    private final UserStickerMapper mapper;

    public List<UserStickerResponseDTO> execute() {
        User authenticatedUser = securityUtils.getAuthenticatedUser();

        return userStickerRepository.findByUserId(authenticatedUser.getId())
                .stream()
                .map(mapper::toDTO)
                .toList();
    }
}

