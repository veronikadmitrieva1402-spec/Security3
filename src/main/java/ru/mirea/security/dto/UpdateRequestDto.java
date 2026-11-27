package ru.mirea.security.dto;

import ru.mirea.security.domain.RequestStatus;

public class UpdateRequestDto {
    public String objectAddress;
    public String serviceTypeId;
    public RequestStatus status;
    public int riskLevel;
    public boolean urgent;
    public Long version;
}
