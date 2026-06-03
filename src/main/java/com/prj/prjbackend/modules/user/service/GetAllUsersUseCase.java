package com.prj.prjbackend.modules.user.service;

import com.prj.prjbackend.modules.user.dto.UserResponseDTO;
import com.prj.prjbackend.modules.user.mapper.IUserMapper;
import com.prj.prjbackend.modules.user.mapper.UserMapper;
import com.prj.prjbackend.modules.user.repository.IUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class GetAllUsersUseCase {
    private final IUserRepository userRepository;
    private final IUserMapper userMapper;

    public GetAllUsersUseCase(IUserRepository userRepository, UserMapper userMapper){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public Page<UserResponseDTO> execute(final int size, final int page){
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());

        return userRepository.findAll(pageable).map(userMapper::toDTO);
    }
}
