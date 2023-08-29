package ru.chulkova.residents.accounting.util;

import ru.chulkova.residents.accounting.dto.ProfileDto;
import ru.chulkova.residents.accounting.model.User;

public class ProfileMapper {

    public static ProfileDto getProfile(User user) {
        return ProfileDto.builder()
                .id(user.id())
                .name(user.getName())
                .age(user.getAge())
                .build();
    }
}