package com.prj.prjbackend.modules.sticker.service;

import com.prj.prjbackend.infra.exception.sticker.StickerNotFoundException;
import com.prj.prjbackend.modules.sticker.dto.StickerImageDTO;
import com.prj.prjbackend.modules.sticker.repository.IStickerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetStickerImageUseCase {
    private final IStickerRepository stickerRepository;

    @Transactional
    public StickerImageDTO execute(final UUID id) {
        return stickerRepository.findImageDataById(id)
                .orElseThrow(() -> new StickerNotFoundException("Figurinha não encontrada com o ID fornecido: " + id));
    }
}

