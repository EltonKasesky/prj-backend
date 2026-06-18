package com.prj.prjbackend.modules.statistics.service;

import com.prj.prjbackend.infra.exception.profile.ProfileNotFoundException;
import com.prj.prjbackend.modules.profile.Profile;
import com.prj.prjbackend.modules.profile.repository.IProfileRepository;
import com.prj.prjbackend.modules.statistics.dto.HomePageResponseDTO;
import com.prj.prjbackend.modules.user.User;
import com.prj.prjbackend.modules.user.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetStatsForHomePageUseCase {
    private final IUserRepository userRepository;
    private final IProfileRepository profileRepository;

    public HomePageResponseDTO execute(){
        List<User> users = userRepository.findAll();
        Profile profile = profileRepository.findByName("ROLE_AUTHOR").orElseThrow(
                () -> new ProfileNotFoundException("O perfil não pode ser encontrado.")
        );

        List<User> authors = users.stream().filter(user -> user.getProfiles().contains(profile)).toList();

        //TODO: Atualizar quando criado album, firuginhas e times para contabilizar
        return new HomePageResponseDTO(authors.size(), 0, 0, 0);
    }
}
