package com.prj.prjbackend.modules.sticker.service;

import com.prj.prjbackend.infra.exception.sticker.InvalidStickerPageException;
import com.prj.prjbackend.infra.exception.sticker.StickerAlreadyExistsException;
import com.prj.prjbackend.infra.exception.sticker.StickerNotFoundException;
import com.prj.prjbackend.modules.album.Album;
import com.prj.prjbackend.modules.album.repository.IAlbumRepository;
import com.prj.prjbackend.modules.sticker.Sticker;
import com.prj.prjbackend.modules.sticker.dto.StickerRegisterRequestDTO;
import com.prj.prjbackend.modules.sticker.dto.StickerResponseDTO;
import com.prj.prjbackend.modules.sticker.mapper.IStickerMapper;
import com.prj.prjbackend.modules.sticker.repository.IStickerRepository;
import com.prj.prjbackend.util.Md5Utils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateStickerUseCase {

    private final IStickerRepository stickerRepository;
    private final IAlbumRepository albumRepository;
    private final IStickerMapper stickerMapper;

    @Transactional
    public StickerResponseDTO execute(final StickerRegisterRequestDTO request) {
        if (stickerRepository.existsByName(request.name())
                || stickerRepository.existsByPageAndNumber(request.page(), request.number())) {
            throw new StickerAlreadyExistsException("Esta figurinha já existe.");
        }

        Album album = albumRepository.findFirstByOrderByIdAsc()
                .orElseThrow(() -> new StickerNotFoundException("Álbum não encontrado."));

        if (request.page() > album.getTotalPages()) {
            throw new InvalidStickerPageException(
                    "A página informada (" + request.page() + ") excede o total de páginas do álbum (" + album.getTotalPages() + ")."
            );
        }

        String tag = Md5Utils.generate(request.image());

        if (stickerRepository.existsByTag(tag)) {
            throw new StickerAlreadyExistsException("Esta figurinha já existe (tag duplicada).");
        }

        Sticker sticker = stickerMapper.toEntity(request, tag, album);
        Sticker saved = stickerRepository.saveAndFlush(sticker);

        return stickerMapper.toDTO(saved);
    }
}