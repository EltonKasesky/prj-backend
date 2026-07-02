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
public class GetFilteredStickersUseCase {
    private final IStickerRepository stickerRepository;
    private final IStickerMapper stickerMapper;

    public Page<StickerResponseDTO> execute(
            final int page,
            final int size,
            final String name,
            final Integer stickerPage,
            final String tag
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return stickerRepository.findByFilters(name, stickerPage, tag, pageable).map(stickerMapper::toDTO);
    }
}
