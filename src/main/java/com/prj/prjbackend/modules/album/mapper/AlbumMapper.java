package com.prj.prjbackend.modules.album.mapper;

import com.prj.prjbackend.modules.album.Album;
import com.prj.prjbackend.modules.album.dto.AlbumDetailsDTO;
import com.prj.prjbackend.modules.sticker.dto.StickerResponseDTO;
import com.prj.prjbackend.modules.sticker.mapper.IStickerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AlbumMapper implements IAlbumMapper {
    private final IStickerMapper stickerMapper;

    @Override
    public AlbumDetailsDTO toDetailsDTO(Album album) {
        List<StickerResponseDTO> stickerDTOs = Collections.emptyList();
        if (album.getStickers() != null) {
            stickerDTOs = album.getStickers().stream()
                    .map(stickerMapper::toDTO)
                    .toList();
        }
        return new AlbumDetailsDTO(
                album.getId(),
                album.getTitle(),
                "/album/cover-image",
                album.getTotalPages(),
                album.getTotalStickers(),
                stickerDTOs
        );
    }
}
