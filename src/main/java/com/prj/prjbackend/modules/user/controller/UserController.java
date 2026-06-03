package com.prj.prjbackend.modules.user.controller;

import com.prj.prjbackend.modules.user.dto.UserResponseDTO;
import com.prj.prjbackend.modules.user.service.GetAllUsersUseCase;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final GetAllUsersUseCase getAllUsersUseCase;

    public UserController(GetAllUsersUseCase getAllUsersUseCase){
        this.getAllUsersUseCase = getAllUsersUseCase;
    }

    @GetMapping
    public ResponseEntity<Page<UserResponseDTO>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return ResponseEntity.ok().body(getAllUsersUseCase.execute(size, page));
    }
}
