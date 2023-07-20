package com.JWT.controllers;

import com.JWT.controllers.request.CreateUserDTO;
import com.JWT.entities.ERole;
import com.JWT.entities.RoleEntity;
import com.JWT.entities.UserEntity;
import com.JWT.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class IndexController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/index")
    public String index() {
        return "Hello World NOT Secured";
    }

    @GetMapping("/indexsecured")
    public String indexSecured() {
        return "Hello World Secured";
    }

    @PostMapping("/createuser")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserDTO createUserDTO) {

        Set<RoleEntity> roles = createUserDTO.getRoles().stream()
                .map(role -> RoleEntity.builder().name(ERole.valueOf(role)).build())
                .collect(Collectors.toSet());

        UserEntity userEntity = UserEntity.builder()
                .email(createUserDTO.getEmail())
                .username(createUserDTO.getUsername())
                .password(passwordEncoder.encode(createUserDTO.getPassword()))
                .roles(roles)
                .build();

        userRepository.save(userEntity);

        return ResponseEntity.ok(userEntity);
    }

    @DeleteMapping("/deleteuser")
    public ResponseEntity<?> deleteUser(@RequestParam String id) {
        userRepository.deleteById(Long.parseLong(id));
        return ResponseEntity.ok("User ".concat(id).concat(" deleted"));
    }
    
}
