package ru.mirea.security.dto;

import java.util.Map;

public class AnalyticsResponse {
    public long totalRequests;
    public long activeRequests;
    public long archivedRequests;
    public long urgentRequests;
    public double averageRiskLevel;
    public Map<String, Long> byStatus;
    public Map<String, Long> byServiceType;
}
