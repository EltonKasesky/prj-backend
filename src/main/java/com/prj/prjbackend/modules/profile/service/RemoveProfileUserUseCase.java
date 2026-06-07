package com.prj.prjbackend.modules.profile.service;

import com.prj.prjbackend.infra.exception.profile.ProfileDisabledException;
import com.prj.prjbackend.infra.exception.profile.ProfileNotFoundException;
import com.prj.prjbackend.infra.exception.profile.UserAlreadyHaveProfileException;
import com.prj.prjbackend.infra.exception.user.UserDisabledException;
import com.prj.prjbackend.infra.exception.user.UserNotFoundException;
import com.prj.prjbackend.modules.profile.Profile;
import com.prj.prjbackend.modules.profile.dto.ProfileToUserRequestDTO;
import com.prj.prjbackend.modules.profile.repository.IProfileRepository;
import com.prj.prjbackend.modules.user.User;
import com.prj.prjbackend.modules.user.repository.IUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RemoveProfileUserUseCase {
    private final IUserRepository userRepository;
    private final IProfileRepository profileRepository;

    @Transactional
    public void execute(final ProfileToUserRequestDTO request) {
        User user = userRepository.findById(request.userId()).orElseThrow(
                () -> new UserNotFoundException("O usuário não pode ser encontrado.")
        );

        Profile profile = profileRepository.findByName(request.profileName()).orElseThrow(
                () -> new ProfileNotFoundException("O perfil não pode ser encontrado.")
        );

        if (user.getStatus().equals(false))
            throw new UserDisabledException("O usuário está desativado.");

        if (profile.getStatus().equals(false))
            throw new ProfileDisabledException("O perfil está desativado.");

        if (user.getProfiles().stream().noneMatch(p -> p.equals(profile)))
            throw new UserAlreadyHaveProfileException("O usuário não possui o perfil solicitado.");

        user.getProfiles().remove(profile);
        userRepository.save(user);
    }
}
