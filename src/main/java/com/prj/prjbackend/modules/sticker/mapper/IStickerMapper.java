package com.prj.prjbackend.modules.sticker.mapper;

import com.prj.prjbackend.modules.album.Album;
import com.prj.prjbackend.modules.sticker.Sticker;
import com.prj.prjbackend.modules.sticker.dto.StickerRegisterRequestDTO;
import com.prj.prjbackend.modules.sticker.dto.StickerResponseDTO;
import com.prj.prjbackend.modules.sticker.dto.StickerUpdateRequestDTO;

public interface IStickerMapper {
    StickerResponseDTO toDTO(Sticker sticker);
    Sticker toEntity(StickerRegisterRequestDTO request, String generatedTag, Album album);
    void updateEntity(Sticker sticker, StickerUpdateRequestDTO request, String tag);
}
