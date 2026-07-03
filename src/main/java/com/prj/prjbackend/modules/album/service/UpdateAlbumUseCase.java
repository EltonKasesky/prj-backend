package com.prj.prjbackend.modules.album.service;

import com.prj.prjbackend.infra.exception.album.AlbumNotFoundException;
import com.prj.prjbackend.modules.album.Album;
import com.prj.prjbackend.modules.album.dto.AlbumDetailsDTO;
import com.prj.prjbackend.modules.album.dto.AlbumUpdateRequestDTO;
import com.prj.prjbackend.modules.album.mapper.IAlbumMapper;
import com.prj.prjbackend.modules.album.repository.IAlbumRepository;
import com.prj.prjbackend.util.ImageTypeUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateAlbumUseCase {

    private final IAlbumRepository albumRepository;
    private final IAlbumMapper albumMapper;

    @Transactional
    public AlbumDetailsDTO execute(final AlbumUpdateRequestDTO request) {
        Album album = albumRepository.findFirstByOrderByIdAsc()
                .orElseThrow(() -> new AlbumNotFoundException("Álbum não encontrado."));

        album.setTitle(request.title());
        album.setTotalPages(request.totalPages());
        album.setTotalStickers(request.totalStickers());

        if (request.coverImage() != null && request.coverImage().length > 0) {
            album.setCoverImage(request.coverImage());
            album.setCoverImageType(ImageTypeUtils.detect(request.coverImage()));
        }

        Album saved = albumRepository.save(album);

        return albumMapper.toDetailsDTO(saved);
    }
}
