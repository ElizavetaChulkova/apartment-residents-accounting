package ru.chulkova.residents.accounting.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto {

    private String name;

    private Integer age;

    private ApartmentDto residence;

    private List<ApartmentDto> properties;
}