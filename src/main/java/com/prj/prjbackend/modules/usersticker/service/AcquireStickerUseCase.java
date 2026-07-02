package com.prj.prjbackend.modules.usersticker.service;

import com.prj.prjbackend.infra.exception.sticker.StickerNotFoundException;
import com.prj.prjbackend.middleware.security.SecurityUtils;
import com.prj.prjbackend.modules.sticker.Sticker;
import com.prj.prjbackend.modules.sticker.repository.IStickerRepository;
import com.prj.prjbackend.modules.user.User;
import com.prj.prjbackend.modules.usersticker.UserSticker;
import com.prj.prjbackend.modules.usersticker.dto.AcquireStickerRequestDTO;
import com.prj.prjbackend.modules.usersticker.dto.UserStickerResponseDTO;
import com.prj.prjbackend.modules.usersticker.mapper.UserStickerMapper;
import com.prj.prjbackend.modules.usersticker.repository.IUserStickerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AcquireStickerUseCase {

    private final IUserStickerRepository userStickerRepository;
    private final IStickerRepository stickerRepository;
    private final UserStickerMapper mapper;
    private final SecurityUtils securityUtils;

    @Transactional
    public UserStickerResponseDTO execute(AcquireStickerRequestDTO request) {
        User authenticatedUser = securityUtils.getAuthenticatedUser();

        Sticker sticker = stickerRepository.findByTag(request.tag())
                .orElseThrow(() ->
                        new StickerNotFoundException("Sticker não encontrada."));

        UserSticker userSticker = userStickerRepository
                .findByUserIdAndStickerId(
                        authenticatedUser.getId(),
                        sticker.getId()
                )
                .orElse(null);

        if (userSticker == null) {
            userSticker = new UserSticker();
            userSticker.setUser(authenticatedUser);
            userSticker.setSticker(sticker);
            userSticker.setQuantity(1);
        } else {
            userSticker.setQuantity(userSticker.getQuantity() + 1);
        }

        userStickerRepository.save(userSticker);

        return mapper.toDTO(userSticker);
    }
}