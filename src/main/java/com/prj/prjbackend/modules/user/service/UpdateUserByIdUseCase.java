package com.prj.prjbackend.modules.user.service;

import com.prj.prjbackend.infra.exception.user.UserNotFoundException;
import com.prj.prjbackend.modules.user.User;
import com.prj.prjbackend.modules.user.dto.UserUpdateRequestDTO;
import com.prj.prjbackend.modules.user.repository.IUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateUserByIdUseCase {
    private final IUserRepository userRepository;

    @Transactional
    public void execute(final UUID id, final UserUpdateRequestDTO request){
        User user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("O usuário não pode ser encontrado.")
        );

        user.setName(request.name());

        userRepository.save(user);
    }
}
