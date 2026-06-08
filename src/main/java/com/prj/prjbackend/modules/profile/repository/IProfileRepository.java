package com.prj.prjbackend.modules.profile.repository;

import com.prj.prjbackend.modules.profile.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IProfileRepository extends JpaRepository<Profile, UUID> {
    Optional<Profile> findByName(String name);
}
