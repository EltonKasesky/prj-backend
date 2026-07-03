package com.prj.prjbackend.modules.sticker.service;

import com.prj.prjbackend.infra.exception.sticker.StickerNotFoundException;
import com.prj.prjbackend.infra.exception.usersticker.UserStickerAlreadyAcquiredException;
import com.prj.prjbackend.modules.sticker.Sticker;
import com.prj.prjbackend.modules.sticker.repository.IStickerRepository;
import com.prj.prjbackend.modules.usersticker.repository.IUserStickerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteStickerUseCase {

    private final IStickerRepository stickerRepository;
    private final IUserStickerRepository userStickerRepository;

    @Transactional
    public void execute(final UUID id) {
        Sticker sticker = stickerRepository.findById(id)
                .orElseThrow(() -> new StickerNotFoundException("Figurinha não encontrada com o ID fornecido: " + id));

        if (userStickerRepository.existsByStickerId(id)) {
            throw new UserStickerAlreadyAcquiredException("Não é possível excluir esta figurinha, pois ela já foi adquirida por algum usuário.");
        }

        stickerRepository.delete(sticker);
    }
}