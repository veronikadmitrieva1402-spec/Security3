package ru.mirea.security.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mirea.security.domain.RequestStatus;
import ru.mirea.security.domain.SecurityRequest;
import ru.mirea.security.domain.ServiceType;
import ru.mirea.security.dto.CreateRequestDto;
import ru.mirea.security.dto.SecurityRequestDto;
import ru.mirea.security.dto.UpdateRequestDto;
import ru.mirea.security.error.NotFoundException;
import ru.mirea.security.repository.SecurityRequestRepository;
import ru.mirea.security.repository.ServiceTypeRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RequestService {
    private final SecurityRequestRepository requestRepository;
    private final ServiceTypeRepository serviceTypeRepository;

    public RequestService(SecurityRequestRepository requestRepository, ServiceTypeRepository serviceTypeRepository) {
        this.requestRepository = requestRepository;
        this.serviceTypeRepository = serviceTypeRepository;
    }

    public Page<SecurityRequest> findAll(Boolean archived, RequestStatus status, String query, Pageable pageable) {
        if (pageable.getPageSize() > 100) {
            throw new NotFoundException("Размер страницы не должен превышать 100");
        }
        return requestRepository.findWithFilters(archived, status, query, pageable);
    }

    @Transactional
    public SecurityRequest create(CreateRequestDto dto) {
        ServiceType type = serviceTypeRepository.findById(UUID.fromString(dto.serviceTypeId))
                .orElseThrow(() -> new NotFoundException("Тип охраны не найден"));

        SecurityRequest req = new SecurityRequest();
        req.setObjectAddress(dto.objectAddress);
        req.setServiceType(type);
        req.setRiskLevel(dto.riskLevel);
        req.setUrgent(dto.urgent);
        req.setRequestNumber(dto.requestNumber != null ? dto.requestNumber : "REQ-" + System.currentTimeMillis());

        return requestRepository.save(req);
    }

    @Transactional
    public SecurityRequest update(UUID id, UpdateRequestDto dto) {
        SecurityRequest req = requestRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Заявка не найдена"));

        if (dto.version != null && !dto.version.equals(req.getVersion())) {
            throw new NotFoundException("Конфликт версий! Данные устарели, обновите страницу.");
        }

        if (dto.objectAddress != null) req.setObjectAddress(dto.objectAddress);
        if (dto.status != null) req.setStatus(dto.status);
        if (dto.riskLevel > 0) req.setRiskLevel(dto.riskLevel);
        req.setUrgent(dto.urgent);
        req.setUpdatedAt(LocalDateTime.now());

        if (dto.serviceTypeId != null) {
            ServiceType type = serviceTypeRepository.findById(UUID.fromString(dto.serviceTypeId))
                    .orElseThrow(() -> new NotFoundException("Тип охраны не найден"));
            req.setServiceType(type);
        }

        return requestRepository.save(req);
    }

    @Transactional
    public void archive(UUID id) {
        SecurityRequest req = requestRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Заявка не найдена"));
        req.setArchived(true);
        requestRepository.save(req);
    }

    @Transactional
    public void restore(UUID id) {
        SecurityRequest req = requestRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Заявка не найдена"));
        req.setArchived(false);
        requestRepository.save(req);
    }
}