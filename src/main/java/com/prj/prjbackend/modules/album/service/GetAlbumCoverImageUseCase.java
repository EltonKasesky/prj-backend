package com.prj.prjbackend.modules.album.service;

import com.prj.prjbackend.infra.exception.album.AlbumNotFoundException;
import com.prj.prjbackend.modules.album.Album;
import com.prj.prjbackend.modules.album.dto.AlbumImageDTO;
import com.prj.prjbackend.modules.album.repository.IAlbumRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAlbumCoverImageUseCase {

    private final IAlbumRepository albumRepository;

    @Transactional
    public AlbumImageDTO execute() {
        Album album = albumRepository.findFirstByOrderByIdAsc()
                .orElseThrow(() -> new AlbumNotFoundException("Álbum não encontrado."));

        return albumRepository.findCoverImageDataById(album.getId())
                .orElseThrow(() -> new AlbumNotFoundException("Álbum não encontrado."));
    }
}
