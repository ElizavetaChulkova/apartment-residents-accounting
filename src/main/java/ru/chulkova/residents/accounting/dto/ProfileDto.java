package ru.chulkova.residents.accounting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.chulkova.residents.accounting.model.AbstractBaseEntity;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto extends AbstractBaseEntity {

    private String name;

    private Integer age;
}