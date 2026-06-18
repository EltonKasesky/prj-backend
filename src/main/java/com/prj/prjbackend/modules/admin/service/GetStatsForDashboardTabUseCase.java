package com.prj.prjbackend.modules.admin.service;

import com.prj.prjbackend.modules.admin.dto.DashboardTabResponseDTO;
import com.prj.prjbackend.modules.user.User;
import com.prj.prjbackend.modules.user.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetStatsForDashboardTabUseCase {
    private final IUserRepository userRepository;

    public DashboardTabResponseDTO execute() {
        List<User> users = userRepository.findAll();
        List<User> activeUsers = users.stream().filter(user -> user.getStatus().equals(true)).toList();

        //TODO: Atualizar quando criado album e firuginhas para contabilizar
        return new DashboardTabResponseDTO(activeUsers.size(), 0, 0);
    }
}
