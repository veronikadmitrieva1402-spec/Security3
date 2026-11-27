package ru.mirea.security.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateRequestDto {
    @NotBlank(message = "Адрес не может быть пустым")
    public String objectAddress;

    @NotNull(message = "Тип охраны обязателен")
    public String serviceTypeId;

    public String requestNumber;
    public int riskLevel = 1;
    public boolean urgent = false;
}
