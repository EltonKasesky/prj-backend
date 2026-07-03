package com.prj.prjbackend.modules.user.service;

import com.prj.prjbackend.modules.user.dto.UserResponseDTO;
import com.prj.prjbackend.modules.user.mapper.IUserMapper;
import com.prj.prjbackend.modules.user.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetFilteredUsersUseCase {
    private final IUserRepository userRepository;
    private final IUserMapper userMapper;

    public Page<UserResponseDTO> execute(
            final int size,
            final int page,
            final String name,
            final String email,
            final String profile
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());

        return userRepository.findByFilters(name, email, profile, pageable).map(userMapper::toDTO);
    }
}
