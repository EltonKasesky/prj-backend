package com.prj.prjbackend.modules.usersticker.repository;

import com.prj.prjbackend.modules.usersticker.UserSticker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserStickerRepository extends JpaRepository<UserSticker, UUID> {
    Optional<UserSticker> findByUserIdAndStickerId(UUID userId, UUID stickerId);
    Optional<UserSticker> findByIdAndUserId(UUID id, UUID userId);

    long countByUserId(UUID userId);
    boolean existsByStickerId(UUID stickerId);

    List<UserSticker> findByUserId(UUID userId);
    void deleteByUserId(UUID userId);
}
