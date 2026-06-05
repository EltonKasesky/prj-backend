package com.prj.prjbackend.modules.user.controller;

import com.prj.prjbackend.modules.user.dto.UserRegisterRequestDTO;
import com.prj.prjbackend.modules.user.dto.UserResponseDTO;
import com.prj.prjbackend.modules.user.dto.UserUpdateRequestDTO;
import com.prj.prjbackend.modules.user.service.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    private final GetAllUsersUseCase getAllUsersUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DisableUserUseCase disableUserUseCase;

    public UserController(GetAllUsersUseCase getAllUsersUseCase,
                          GetUserByIdUseCase getUserByIdUseCase,
                          CreateUserUseCase createUserUseCase,
                          UpdateUserUseCase updateUserUseCase,
                          DisableUserUseCase disableUserUseCase){
        this.getAllUsersUseCase = getAllUsersUseCase;
        this.getUserByIdUseCase = getUserByIdUseCase;
        this.createUserUseCase = createUserUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.disableUserUseCase = disableUserUseCase;
    }

    @GetMapping
    public ResponseEntity<Page<UserResponseDTO>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return ResponseEntity.ok().body(getAllUsersUseCase.execute(size, page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@Valid @PathVariable UUID id){
        return ResponseEntity.ok().body(getUserByIdUseCase.execute(id));
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody UserRegisterRequestDTO request){
        createUserUseCase.execute(request);
        return ResponseEntity.created(URI.create("")).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@Valid @PathVariable UUID id, @Valid @RequestBody UserUpdateRequestDTO request){
        updateUserUseCase.execute(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> disableUser(@PathVariable UUID id){
        disableUserUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
