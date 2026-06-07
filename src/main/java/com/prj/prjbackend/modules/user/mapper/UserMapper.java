package com.prj.prjbackend.modules.user.mapper;

import com.prj.prjbackend.modules.user.User;
import com.prj.prjbackend.modules.user.dto.UserRegisterRequestDTO;
import com.prj.prjbackend.modules.user.dto.UserResponseDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements IUserMapper {
    public UserResponseDTO toDTO(User user){
        return new UserResponseDTO(
            user.getId(),
            user.getName(),
            user.getEmail()
        );
    }

    @Override
    public User toEntity(UserRegisterRequestDTO request) {
        User user = new User();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        return user;
    }
}
