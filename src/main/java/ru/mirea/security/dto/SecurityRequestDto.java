package ru.mirea.security.dto;

import ru.mirea.security.domain.RequestStatus;
import java.time.LocalDateTime;
import java.util.UUID;

public class SecurityRequestDto {
    public UUID id;
    public String requestNumber;
    public String objectAddress;
    public String serviceType;
    public RequestStatus status;
    public int riskLevel;
    public boolean urgent;
    public boolean archived;
    public Long version;
    public LocalDateTime createdAt;


}
