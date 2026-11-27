package ru.mirea.security.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "security_requests")
public class SecurityRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "request_number", unique = true, nullable = false)
    private String requestNumber;

    @Column(nullable = false)
    private String objectAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_type_id", nullable = false)
    private ServiceType serviceType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus status = RequestStatus.NEW;

    @Column(nullable = false)
    private int riskLevel;

    @Column(nullable = false)
    private boolean urgent = false;

    @Column(nullable = false)
    private boolean archived = false;

    @Version
    private Long version;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;

    public UUID getId() { return id; }
    public void setId(UUID id) {this.id = id; }

    public String getRequestNumber() { return requestNumber; }
    public void setRequestNumber(String requestNumber) {this.requestNumber = requestNumber; }

    public String getObjectAddress() { return objectAddress; }
    public void setObjectAddress(String objectAddress) {this.objectAddress = objectAddress; }

    public ServiceType getServiceType() { return serviceType; }
    public void setServiceType(ServiceType serviceType) {this.serviceType = serviceType; }

    public RequestStatus getStatus() { return status; }
    public void setStatus(RequestStatus status) { this.status = status; }

    public int getRiskLevel() { return riskLevel; }
    public void setRiskLevel(int riskLevel) { this.riskLevel = riskLevel; }

    public boolean isUrgent() { return urgent; }
    public void setUrgent(boolean urgent) { this.urgent = urgent; }

    public boolean isArchived() { return archived; }
    public void setArchived(boolean archived) { this.archived = archived; }

    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
