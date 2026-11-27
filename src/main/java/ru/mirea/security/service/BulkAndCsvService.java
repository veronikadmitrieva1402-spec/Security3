package ru.mirea.security.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.mirea.security.domain.RequestStatus;
import ru.mirea.security.domain.SecurityRequest;
import ru.mirea.security.repository.SecurityRequestRepository;

import java.io.*;
import java.util.*;

@Service
public class BulkAndCsvService {
    private final SecurityRequestRepository repository;

    public BulkAndCsvService(SecurityRequestRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void bulkArchive(List<UUID> ids) {
        List<SecurityRequest> reqs = repository.findAllById(ids);
        reqs.forEach(r -> r.setArchived(true));
        repository.saveAll(reqs);
    }

    @Transactional
    public void bulkStatus(List<UUID> ids, RequestStatus status) {
        List<SecurityRequest> reqs = repository.findAllById(ids);
        reqs.forEach(r -> r.setStatus(status));
        repository.saveAll(reqs);
    }

    public void exportToCsv(Writer writer) throws IOException {
        List<SecurityRequest> reqs = repository.findAll();
        try (CSVWriter csvWriter = new CSVWriter(writer)) {
            csvWriter.writeNext(new String[]{"ID", "Address", "Type", "Status", "Risk", "Archived"});
            for (SecurityRequest r : reqs) {
                csvWriter.writeNext(new String[]{
                        r.getId().toString(),
                        r.getObjectAddress(),
                        r.getServiceType().getName(),
                        r.getStatus().name(),
                        String.valueOf(r.getRiskLevel()),
                        String.valueOf(r.isArchived())
                });
            }
        }
    }

    public Map<String, Object> previewCsv(MultipartFile file) throws Exception {
        int valid = 0; int invalid = 0;
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CSVReader csvReader = new CSVReader(reader);
            List<String[]> lines = csvReader.readAll();
            for (int i = 1; i < lines.size(); i++) { // skip header
                String[] line = lines.get(i);
                if (line.length >= 2 && !line[1].trim().isEmpty()) valid++;
                else invalid++;
            }
        }
        return Map.of("validRows", valid, "invalidRows", invalid);
    }
}