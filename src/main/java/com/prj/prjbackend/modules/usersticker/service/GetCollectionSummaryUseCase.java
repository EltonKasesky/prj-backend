package com.prj.prjbackend.modules.usersticker.service;

import com.prj.prjbackend.middleware.security.SecurityUtils;
import com.prj.prjbackend.modules.sticker.repository.IStickerRepository;
import com.prj.prjbackend.modules.user.User;
import com.prj.prjbackend.modules.usersticker.dto.AlbumProgressResponseDTO;
import com.prj.prjbackend.modules.usersticker.repository.IUserStickerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetCollectionSummaryUseCase {

    private final IUserStickerRepository userStickerRepository;
    private final IStickerRepository stickerRepository;
    private final SecurityUtils securityUtils;

    public AlbumProgressResponseDTO execute() {
        User authenticatedUser = securityUtils.getAuthenticatedUser();

        int collected = Math.toIntExact(
                userStickerRepository.countByUserId(authenticatedUser.getId())
        );

        int total = Math.toIntExact(
                stickerRepository.count()
        );

        double percentage = total == 0
                ? 0.0
                : Math.round((collected * 10000.0) / total) / 100.0;

        return new AlbumProgressResponseDTO(
                collected,
                total,
                percentage
        );
    }
}
