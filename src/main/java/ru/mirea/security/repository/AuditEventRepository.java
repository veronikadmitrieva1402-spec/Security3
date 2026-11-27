package ru.mirea.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.security.domain.AuditEvent;
import java.util.UUID;

public interface AuditEventRepository extends JpaRepository<AuditEvent, UUID> {
}