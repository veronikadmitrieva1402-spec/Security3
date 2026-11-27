package ru.mirea.security.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.mirea.security.domain.RequestStatus;
import ru.mirea.security.domain.SecurityRequest;

import java.util.UUID;

public interface SecurityRequestRepository extends JpaRepository<SecurityRequest, UUID> {
    @Query("SELECT r FROM SecurityRequest r WHERE " +
            "(:archived IS NULL OR r.archived = :archived) AND " +
            "(:status IS NULL OR r.status = :status) AND " +
            "(:query IS NULL OR LOWER(r.objectAddress) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(r.requestNumber) LIKE LOWER(CONCAT('%', :query, '%')))")
    Page<SecurityRequest> findWithFilters(
            @Param("archived") Boolean archived,
            @Param("status") RequestStatus status,
            @Param("query") String query,
            Pageable pageable
    );

}
