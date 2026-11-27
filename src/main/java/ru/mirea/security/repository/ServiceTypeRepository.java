package ru.mirea.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.security.domain.ServiceType;
import java.util.UUID;

public interface ServiceTypeRepository extends JpaRepository<ServiceType, UUID> {

}
