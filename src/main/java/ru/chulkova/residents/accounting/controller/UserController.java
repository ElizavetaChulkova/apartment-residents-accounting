package ru.chulkova.residents.accounting.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.chulkova.residents.accounting.dto.ProfileDto;
import ru.chulkova.residents.accounting.dto.UserDto;
import ru.chulkova.residents.accounting.model.Apartment;
import ru.chulkova.residents.accounting.model.User;
import ru.chulkova.residents.accounting.repository.ApartmentRepository;
import ru.chulkova.residents.accounting.repository.UserRepository;
import ru.chulkova.residents.accounting.util.ProfileMapper;

@Slf4j
@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository repository;
    private final ApartmentRepository apartmentRepository;
    private final PasswordEncoder encoder;

    @PostMapping("/add-resident")
    public ResponseEntity<?> addResident(@AuthenticationPrincipal User user,
                                         @RequestParam("resident") String residentName,
                                         @RequestParam("address") String address) {
        log.info("add new resident to property {}", address);
        Apartment apartment = apartmentRepository.findApartmentByAddress(address)
                .orElseThrow(() -> new RuntimeException("Residence not found"));
        User resident = repository.findByName(residentName).orElseThrow();
        if (apartment.getOwner().getId() == user.id()) {
            resident.setResidence(apartment);
            repository.save(resident);
        } else {
            throw new RuntimeException("You are not the owner of this house");
        }
        return ResponseEntity.ok("Resident added successfully");
    }

    @GetMapping
    public ProfileDto getProfile(@AuthenticationPrincipal User user) {
        log.info("get user profile {}", user.getName());
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
        log.info("delete user");
        repository.delete(user);
        return ResponseEntity.ok("You deleted your account");
    }
}