package com.prj.prjbackend.modules.user.service;

import com.prj.prjbackend.infra.exception.profile.ProfileNotFoundException;
import com.prj.prjbackend.infra.exception.user.UserAlreadyExistsException;
import com.prj.prjbackend.modules.profile.Profile;
import com.prj.prjbackend.modules.profile.repository.IProfileRepository;
import com.prj.prjbackend.modules.user.User;
import com.prj.prjbackend.modules.user.dto.UserRegisterRequestDTO;
import com.prj.prjbackend.modules.user.mapper.IUserMapper;
import com.prj.prjbackend.modules.user.repository.IUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserUseCase {
    private final IUserRepository userRepository;
    private final IProfileRepository profileRepository;
    private final IUserMapper userMapper;

    @Transactional
    public void execute(final UserRegisterRequestDTO request){
        userRepository.findByEmail(request.email()).ifPresent(user -> {
            throw new UserAlreadyExistsException("Esse email já pertence a um usuário.");
        });

        User user = userMapper.toEntity(request);

        Profile profile = profileRepository.findByName("ROLE_COLLECTOR").orElseThrow(
                () -> new ProfileNotFoundException("O perfil não pode ser encontrado.")
        );

        user.getProfiles().add(profile);

        userRepository.save(user);
    }
}
