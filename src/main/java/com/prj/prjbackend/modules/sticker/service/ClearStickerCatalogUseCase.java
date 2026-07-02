package com.prj.prjbackend.modules.sticker.service;

import com.prj.prjbackend.modules.sticker.repository.IStickerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClearStickerCatalogUseCase {
    private final IStickerRepository stickerRepository;

    @Transactional
    public void execute() {
        stickerRepository.deleteAll();
    }
}
