package com.prj.prjbackend.modules.sticker.service;

import com.prj.prjbackend.modules.sticker.dto.StickerResponseDTO;
import com.prj.prjbackend.modules.sticker.mapper.IStickerMapper;
import com.prj.prjbackend.modules.sticker.repository.IStickerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAllStickersUseCase {
    private final IStickerRepository stickerRepository;
    private final IStickerMapper stickerMapper;

    public Page<StickerResponseDTO> execute(final int page, final int size) {
        Pageable pageable = PageRequest.of(page, size);
        return stickerRepository.findAll(pageable).map(stickerMapper::toDTO);
    }
}
