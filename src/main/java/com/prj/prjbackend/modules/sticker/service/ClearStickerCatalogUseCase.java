package com.prj.prjbackend.modules.sticker.service;

import com.prj.prjbackend.modules.sticker.repository.IStickerRepository;
import com.prj.prjbackend.modules.usersticker.repository.IUserStickerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClearStickerCatalogUseCase {
    private final IStickerRepository stickerRepository;
    private final IUserStickerRepository userStickerRepository;

    @Transactional
    public void execute() {
        userStickerRepository.deleteAll();
        stickerRepository.deleteAll();
    }
}