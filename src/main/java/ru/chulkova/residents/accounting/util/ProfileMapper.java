package ru.chulkova.residents.accounting.util;

import ru.chulkova.residents.accounting.dto.ProfileDto;
import ru.chulkova.residents.accounting.model.User;

import java.util.stream.Collectors;

public class ProfileMapper {

    public static ProfileDto getProfile(User user) {
        return ProfileDto.builder()
                .name(user.getName())
                .age(user.getAge())
                .properties(user.getProperty()
                        .stream()
                        .map(ApartmentMapper::getTo)
                        .collect(Collectors.toList()))
                .residence(ApartmentMapper.getTo(user.getResidence()))
                .build();
    }
}