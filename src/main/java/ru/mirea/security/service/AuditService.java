package ru.mirea.security.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.mirea.security.domain.AuditEvent;
import ru.mirea.security.repository.AuditEventRepository;

@Service
public class AuditService {
    private final AuditEventRepository repository;

    public AuditService(AuditEventRepository repository) {
        this.repository = repository;
    }

    public void log(String eventType, String entityType, String entityId, String description) {
        AuditEvent event = new AuditEvent();
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            event.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        }
        event.setEventType(eventType);
        event.setEntityType(entityType);
        event.setEntityId(entityId);
        event.setDescription(description);
        repository.save(event);
    }
}