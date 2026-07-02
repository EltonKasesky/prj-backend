package com.prj.prjbackend.modules.album.mapper;

import com.prj.prjbackend.modules.album.Album;
import com.prj.prjbackend.modules.album.dto.AlbumDetailsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlbumMapper implements IAlbumMapper {

    @Override
    public AlbumDetailsDTO toDetailsDTO(Album album) {
        return new AlbumDetailsDTO(
                album.getId(),
                album.getTitle(),
                "/album/cover-image",
                album.getTotalPages()
        );
    }
}
