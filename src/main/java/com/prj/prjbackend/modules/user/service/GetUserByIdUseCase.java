package com.prj.prjbackend.modules.user.service;

import com.prj.prjbackend.infra.user.UserDisabledException;
import com.prj.prjbackend.infra.user.UserNotFoundException;
import com.prj.prjbackend.modules.user.User;
import com.prj.prjbackend.modules.user.dto.UserResponseDTO;
import com.prj.prjbackend.modules.user.mapper.IUserMapper;
import com.prj.prjbackend.modules.user.mapper.UserMapper;
import com.prj.prjbackend.modules.user.repository.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetUserByIdUseCase {
    private final IUserRepository userRepository;
    private final IUserMapper userMapper;

    public GetUserByIdUseCase(IUserRepository userRepository, UserMapper userMapper){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserResponseDTO execute(final UUID id){
        User user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("O usuário não pode ser encontrado.")
        );

        if (user.getStatus().equals(false))
            throw new UserDisabledException("O usuário está desabilitado.");

        return userMapper.toDTO(user);
    }
}
