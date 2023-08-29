package ru.chulkova.residents.accounting.util;

import ru.chulkova.residents.accounting.dto.ApartmentDto;
import ru.chulkova.residents.accounting.dto.ApartmentResponse;
import ru.chulkova.residents.accounting.model.Apartment;
import ru.chulkova.residents.accounting.model.User;

public class ApartmentMapper {

    public static ApartmentResponse getResponse(Apartment apartment, User user) {
        return ApartmentResponse.builder()
                .address(apartment.getAddress())
                .ownerName(user.getName())
                .build();
    }

    public static ApartmentDto getTo(Apartment apartment) {
        return ApartmentDto.builder()
                .address(apartment.getAddress())
                .build();
    }
}