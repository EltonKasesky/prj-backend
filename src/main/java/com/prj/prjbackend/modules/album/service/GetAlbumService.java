package com.prj.prjbackend.modules.album.service;

import com.prj.prjbackend.infra.exception.album.AlbumNotFoundException;
import com.prj.prjbackend.modules.album.dto.AlbumDetailsDTO;
import com.prj.prjbackend.modules.album.mapper.IAlbumMapper;
import com.prj.prjbackend.modules.album.repository.IAlbumRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAlbumService {

    private final IAlbumRepository albumRepository;
    private final IAlbumMapper albumMapper;

    @Transactional
    public AlbumDetailsDTO execute() {
        return albumRepository.findFirstByOrderByIdAsc()
                .map(albumMapper::toDetailsDTO)
                .orElseThrow(() -> new AlbumNotFoundException("Álbum não encontrado."));
    }
}
