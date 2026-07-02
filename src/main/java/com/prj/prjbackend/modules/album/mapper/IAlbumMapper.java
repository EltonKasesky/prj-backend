package com.prj.prjbackend.modules.album.mapper;

import com.prj.prjbackend.modules.album.Album;
import com.prj.prjbackend.modules.album.dto.AlbumDetailsDTO;

public interface IAlbumMapper {
    AlbumDetailsDTO toDetailsDTO(Album album);
}
