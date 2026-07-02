package com.prj.prjbackend.modules.album.repository;

import com.prj.prjbackend.modules.album.Album;
import com.prj.prjbackend.modules.album.dto.AlbumImageDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IAlbumRepository extends JpaRepository<Album, UUID> {
    Optional<Album> findFirstByOrderByIdAsc();

    @Query("SELECT new com.prj.prjbackend.modules.album.dto.AlbumImageDTO(a.coverImage, a.coverImageType) FROM Album a WHERE a.id = :id")
    Optional<AlbumImageDTO> findCoverImageDataById(@Param("id") UUID id);
}
