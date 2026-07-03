package com.prj.prjbackend.modules.usersticker.service;

import com.prj.prjbackend.infra.exception.usersticker.UserStickerNotFoundException;
import com.prj.prjbackend.middleware.security.SecurityUtils;
import com.prj.prjbackend.modules.user.User;
import com.prj.prjbackend.modules.usersticker.UserSticker;
import com.prj.prjbackend.modules.usersticker.repository.IUserStickerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RemoveStickerUseCase {

    private final IUserStickerRepository userStickerRepository;
    private final SecurityUtils securityUtils;

    @Transactional
    public void execute(UUID id) {
        User authenticatedUser = securityUtils.getAuthenticatedUser();

        UserSticker userSticker = userStickerRepository
                .findByIdAndUserId(id, authenticatedUser.getId())
                .orElseThrow(() ->
                        new UserStickerNotFoundException(
                                "Figurinha não encontrada na coleção."
                        ));

        if (userSticker.getQuantity() > 1) {
            userSticker.setQuantity(userSticker.getQuantity() - 1);
            userStickerRepository.save(userSticker);
        } else {
            userStickerRepository.delete(userSticker);
        }
    }
}
