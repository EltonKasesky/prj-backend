package com.prj.prjbackend.modules.sticker.service;

import com.prj.prjbackend.infra.exception.sticker.StickerNotFoundException;
import com.prj.prjbackend.modules.sticker.dto.StickerResponseDTO;
import com.prj.prjbackend.modules.sticker.mapper.IStickerMapper;
import com.prj.prjbackend.modules.sticker.repository.IStickerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetStickerByIdUseCase {

    private final IStickerRepository stickerRepository;
    private final IStickerMapper stickerMapper;

    @Transactional
    public StickerResponseDTO execute(final UUID id) {
        return stickerRepository.findById(id)
                .map(stickerMapper::toDTO)
                .orElseThrow(() -> new StickerNotFoundException("Figurinha não encontrada com o ID fornecido: " + id));
    }
}
