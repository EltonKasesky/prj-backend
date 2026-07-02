package com.prj.prjbackend.modules.usersticker.service;

import com.prj.prjbackend.middleware.security.SecurityUtils;
import com.prj.prjbackend.modules.user.User;
import com.prj.prjbackend.modules.usersticker.repository.IUserStickerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClearUserStickerCollectionUseCase {

    private final IUserStickerRepository userStickerRepository;
    private final SecurityUtils securityUtils;

    @Transactional
    public void execute() {
        User authenticatedUser = securityUtils.getAuthenticatedUser();

        userStickerRepository.deleteByUserId(authenticatedUser.getId());
    }
}
