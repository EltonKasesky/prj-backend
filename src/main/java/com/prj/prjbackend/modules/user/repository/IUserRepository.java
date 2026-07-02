package com.prj.prjbackend.modules.user.repository;

import com.prj.prjbackend.modules.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    @Query("""
            SELECT DISTINCT u FROM User u
            LEFT JOIN u.profiles p
            WHERE (:name IS NULL OR LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%')))
            AND (:email IS NULL OR LOWER(u.email) LIKE LOWER(CONCAT('%', :email, '%')))
            AND (:profile IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :profile, '%')))
            """)
    Page<User> findByFilters(
            @Param("name") String name,
            @Param("email") String email,
            @Param("profile") String profile,
            Pageable pageable
    );
}
