package ru.chulkova.residents.accounting.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.chulkova.residents.accounting.dto.ProfileDto;
import ru.chulkova.residents.accounting.dto.UserDto;
import ru.chulkova.residents.accounting.model.User;
import ru.chulkova.residents.accounting.repository.UserRepository;
import ru.chulkova.residents.accounting.util.ProfileMapper;

@Slf4j
@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    @GetMapping
    public ProfileDto getProfile(@AuthenticationPrincipal User user) {
        return ProfileMapper.getProfile(repository.findByName(user.getName())
                .orElseThrow());
    }

    @PutMapping
    public ProfileDto update(@AuthenticationPrincipal User user,
                             @RequestBody UserDto userDto) {
        log.info("update user");
        user.setAge(userDto.getAge());
        user.setPassword(encoder.encode(userDto.getPassword()));
        return ProfileMapper.getProfile(
                repository.save(user)
        );
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@AuthenticationPrincipal User user) {
        repository.delete(user);
        return ResponseEntity.ok("You deleted your account");
    }
}