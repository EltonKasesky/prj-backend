package com.prj.prjbackend.modules.sticker.service;

import com.prj.prjbackend.infra.exception.sticker.InvalidStickerPageException;
import com.prj.prjbackend.infra.exception.sticker.StickerAlreadyExistsException;
import com.prj.prjbackend.infra.exception.sticker.StickerNotFoundException;
import com.prj.prjbackend.modules.sticker.Sticker;
import com.prj.prjbackend.modules.sticker.dto.StickerResponseDTO;
import com.prj.prjbackend.modules.sticker.dto.StickerUpdateRequestDTO;
import com.prj.prjbackend.modules.sticker.mapper.IStickerMapper;
import com.prj.prjbackend.modules.sticker.repository.IStickerRepository;
import com.prj.prjbackend.util.Md5Utils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateStickerUseCase {

    private final IStickerRepository stickerRepository;
    private final IStickerMapper stickerMapper;

    @Transactional
    public StickerResponseDTO execute(final UUID id, final StickerUpdateRequestDTO request) {
        Sticker sticker = stickerRepository.findById(id)
                .orElseThrow(() -> new StickerNotFoundException("Figurinha não encontrada com o ID fornecido: " + id));

        if (stickerRepository.existsByPageAndNumberAndIdNot(request.page(), request.number(), id)) {
            throw new StickerAlreadyExistsException("Já existe outra figurinha cadastrada nesta página e número.");
        }

        Integer totalPages = sticker.getAlbum().getTotalPages();
        if (request.page() > totalPages) {
            throw new InvalidStickerPageException(
                    "A página informada (" + request.page() + ") excede o total de páginas do álbum (" + totalPages + ")."
            );
        }

        String tag = sticker.getTag();
        boolean imageChanged = request.image() != null
                && request.image().length > 0
                && !Arrays.equals(request.image(), sticker.getImage());

        if (imageChanged) {
            tag = Md5Utils.generate(request.image());

            if (stickerRepository.existsByTagAndIdNot(tag, id)) {
                throw new StickerAlreadyExistsException("Esta imagem já pertence a outra figurinha cadastrada.");
            }
        }

        stickerMapper.updateEntity(sticker, request, tag);
        Sticker saved = stickerRepository.save(sticker);

        return stickerMapper.toDTO(saved);
    }
}
