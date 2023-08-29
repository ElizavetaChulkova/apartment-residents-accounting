package ru.chulkova.residents.accounting.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.chulkova.residents.accounting.dto.ApartmentDto;
import ru.chulkova.residents.accounting.dto.ApartmentResponse;
import ru.chulkova.residents.accounting.model.Apartment;
import ru.chulkova.residents.accounting.model.User;
import ru.chulkova.residents.accounting.repository.ApartmentRepository;
import ru.chulkova.residents.accounting.util.ApartmentMapper;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/apartments")
@RequiredArgsConstructor
public class ApartmentController {

    private final ApartmentRepository repository;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApartmentResponse> create(@AuthenticationPrincipal User user,
                                                    @RequestBody ApartmentDto apartmentDto) {
        Apartment apartment = Apartment.builder()
                .address(apartmentDto.getAddress())
                .owner(user)
                .build();
        repository.save(apartment);
        return ResponseEntity.ok(ApartmentMapper.getResponse(apartment, user));
    }

    @GetMapping
    public List<ApartmentResponse> getAllUserApartments(@AuthenticationPrincipal User user) {
        log.info("find all by owner {}", user.getName());
        return repository.getAllByOwner(user.id())
                .stream()
                .map(apartment -> ApartmentMapper.getResponse(apartment, user))
                .collect(Collectors.toList());
    }

    @PutMapping(value = "/{apartmentId}")
    public ResponseEntity<ApartmentResponse> update(@AuthenticationPrincipal User user,
                                                    @RequestParam String address,
                                                    @PathVariable("apartmentId") Long apartmentId) {
        log.info("update apartment");
        Apartment toUpdate = repository.findById(apartmentId).orElseThrow();
        toUpdate.setAddress(address);
        repository.save(toUpdate);
        return ResponseEntity.ok(ApartmentMapper.getResponse(toUpdate, user));
    }

    @DeleteMapping(value = "/{apartmentId}")
    public void delete(@AuthenticationPrincipal User user,
                       @PathVariable("apartmentId") Long apartmentId) {
        Apartment apartment = repository.findById(apartmentId).orElseThrow();
        repository.delete(apartment);
    }
}