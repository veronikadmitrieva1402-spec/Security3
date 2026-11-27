package ru.mirea.security.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.mirea.security.domain.RequestStatus;
import ru.mirea.security.dto.AnalyticsResponse;
import ru.mirea.security.domain.RequestNote;
import ru.mirea.security.repository.RequestNoteRepository;
import ru.mirea.security.service.AnalyticsService;
import ru.mirea.security.service.AuditService;
import ru.mirea.security.service.BulkAndCsvService;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class AdvancedController {

    private final BulkAndCsvService bulkService;
    private final AnalyticsService analyticsService;
    private final RequestNoteRepository noteRepository;
    private final AuditService auditService;

    public AdvancedController(BulkAndCsvService bulkService, AnalyticsService analyticsService, RequestNoteRepository noteRepository, AuditService auditService) {
        this.bulkService = bulkService;
        this.analyticsService = analyticsService;
        this.noteRepository = noteRepository;
        this.auditService = auditService;
    }

    @GetMapping("/requests/export")
    public void exportCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=requests.csv");
        bulkService.exportToCsv(response.getWriter());
        auditService.log("EXPORT", "SecurityRequest", null, "CSV Export");
    }

    @PostMapping("/requests/import/preview")
    public ResponseEntity<?> previewCsv(@RequestParam("file") MultipartFile file) throws Exception {
        return ResponseEntity.ok(bulkService.previewCsv(file));
    }

    @PostMapping("/requests/bulk/archive")
    public ResponseEntity<?> bulkArchive(@RequestBody List<UUID> ids) {
        bulkService.bulkArchive(ids);
        auditService.log("BULK_ARCHIVE", "SecurityRequest", null, "Archived " + ids.size());
        return ResponseEntity.ok(Map.of("processed", ids.size()));
    }

    @PostMapping("/requests/bulk/status")
    public ResponseEntity<?> bulkStatus(@RequestParam RequestStatus status, @RequestBody List<UUID> ids) {
        bulkService.bulkStatus(ids, status);
        auditService.log("BULK_STATUS", "SecurityRequest", null, "Changed status for " + ids.size());
        return ResponseEntity.ok(Map.of("processed", ids.size()));
    }

    @GetMapping("/requests/{id}/notes")
    public List<RequestNote> getNotes(@PathVariable UUID id) {
        return noteRepository.findByRequestIdOrderByPinnedDescCreatedAtDesc(id);
    }

    @PostMapping("/requests/{id}/notes")
    public RequestNote addNote(@PathVariable UUID id, @RequestBody RequestNote note) {
        note.setRequestId(id);
        if (note.getAuthorName() == null) note.setAuthorName("System");
        return noteRepository.save(note);
    }

    @GetMapping("/analytics/summary")
    public AnalyticsResponse analytics() {
        return analyticsService.getSummary();
    }
}