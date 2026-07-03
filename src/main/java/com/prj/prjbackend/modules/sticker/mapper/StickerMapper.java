package com.prj.prjbackend.modules.sticker.mapper;

import com.prj.prjbackend.modules.album.Album;
import com.prj.prjbackend.modules.sticker.Sticker;
import com.prj.prjbackend.modules.sticker.dto.StickerRegisterRequestDTO;
import com.prj.prjbackend.modules.sticker.dto.StickerResponseDTO;
import com.prj.prjbackend.modules.sticker.dto.StickerUpdateRequestDTO;
import com.prj.prjbackend.util.ImageTypeUtils;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class StickerMapper implements IStickerMapper{
    @Override
    public StickerResponseDTO toDTO(Sticker sticker) {
        String imageUrl = "/stickers/" + sticker.getId() + "/image";
        UUID albumId = (sticker.getAlbum() != null) ? sticker.getAlbum().getId() : null;

        return new StickerResponseDTO(
                sticker.getId(),
                sticker.getName(),
                sticker.getPage(),
                sticker.getNumber(),
                sticker.getTag(),
                sticker.getDescription(),
                imageUrl,
                sticker.getCreatedAt(),
                albumId
        );
    }

    @Override
    public Sticker toEntity(StickerRegisterRequestDTO request, String generatedTag, Album album) {
        Sticker sticker = new Sticker();

        sticker.setName(request.name());
        sticker.setDescription(request.description());
        sticker.setPage(request.page());
        sticker.setNumber(request.number());
        sticker.setImage(request.image());
        sticker.setImageType(ImageTypeUtils.detect(request.image()));
        sticker.setTag(generatedTag);
        sticker.setAlbum(album);

        return sticker;
    }

    @Override
    public void updateEntity(Sticker sticker, StickerUpdateRequestDTO request, String tag) {
        sticker.setName(request.name());
        sticker.setDescription(request.description());
        sticker.setPage(request.page());
        sticker.setNumber(request.number());

        if (request.image() != null && request.image().length > 0) {
            sticker.setImage(request.image());
            sticker.setImageType(ImageTypeUtils.detect(request.image()));
            sticker.setTag(tag);
        }
    }
}
