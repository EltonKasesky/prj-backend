package com.prj.prjbackend.modules.sticker.repository;

import com.prj.prjbackend.modules.sticker.Sticker;
import com.prj.prjbackend.modules.sticker.dto.StickerImageDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface IStickerRepository extends JpaRepository<Sticker, UUID> {
    @Query("SELECT new com.prj.prjbackend.modules.sticker.dto.StickerImageDTO(s.image, s.imageType) FROM Sticker s WHERE s.id = :id")
    Optional<StickerImageDTO> findImageDataById(@Param("id") UUID id);
    Optional<Sticker> findByTag(String tag);

    boolean existsByName(String name);
    boolean existsByPageAndNumber(Integer page, Integer number);
    boolean existsByPageAndNumberAndIdNot(Integer page, Integer number, UUID id);
    boolean existsByTag(String tag);
    boolean existsByTagAndIdNot(String tag, UUID id);

    @Query("""
            SELECT s FROM Sticker s
            WHERE (:name IS NULL OR LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%')))
            AND (:page IS NULL OR s.page = :page)
            AND (:tag IS NULL OR LOWER(s.tag) LIKE LOWER(CONCAT('%', :tag, '%')))
            """)
    Page<Sticker> findByFilters(
            @Param("name") String name,
            @Param("page") Integer page,
            @Param("tag") String tag,
            Pageable pageable
    );
}
