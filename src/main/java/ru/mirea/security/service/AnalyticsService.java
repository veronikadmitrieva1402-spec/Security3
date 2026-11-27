package ru.mirea.security.service;

import org.springframework.stereotype.Service;
import ru.mirea.security.domain.SecurityRequest;
import ru.mirea.security.dto.AnalyticsResponse;
import ru.mirea.security.repository.SecurityRequestRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {
    private final SecurityRequestRepository repository;

    public AnalyticsService(SecurityRequestRepository repository) {
        this.repository = repository;
    }

    public AnalyticsResponse getSummary() {
        List<SecurityRequest> all = repository.findAll();
        AnalyticsResponse res = new AnalyticsResponse();

        res.totalRequests = all.size();
        res.activeRequests = all.stream().filter(r -> !r.isArchived()).count();
        res.archivedRequests = all.stream().filter(SecurityRequest::isArchived).count();
        res.urgentRequests = all.stream().filter(SecurityRequest::isUrgent).count();

        res.averageRiskLevel = all.stream().mapToInt(SecurityRequest::getRiskLevel).average().orElse(0.0);

        res.byStatus = all.stream().collect(Collectors.groupingBy(r -> r.getStatus().name(), Collectors.counting()));
        res.byServiceType = all.stream().collect(Collectors.groupingBy(r -> r.getServiceType().getName(), Collectors.counting()));

        return res;
    }
}
