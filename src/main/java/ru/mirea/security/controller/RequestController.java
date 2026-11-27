package ru.mirea.security.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mirea.security.domain.RequestStatus;
import ru.mirea.security.domain.SecurityRequest;
import ru.mirea.security.dto.CreateRequestDto;
import ru.mirea.security.dto.UpdateRequestDto;
import ru.mirea.security.service.RequestService;

import java.util.UUID;

@RestController
@RequestMapping("/api/requests")
public class RequestController {
    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping
    public ResponseEntity<Page<SecurityRequest>> getAll(
            @RequestParam(required = false) Boolean archived,
            @RequestParam(required = false) RequestStatus status,
            @RequestParam(required = false) String query,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(requestService.findAll(archived, status, query, pageable));
    }

    @PostMapping
    public ResponseEntity<SecurityRequest> create(@Valid @RequestBody CreateRequestDto dto) {
        return ResponseEntity.ok(requestService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SecurityRequest> update(@PathVariable UUID id, @RequestBody UpdateRequestDto dto) {
        return ResponseEntity.ok(requestService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> archive(@PathVariable UUID id) {
        requestService.archive(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/restore")
    public ResponseEntity<Void> restore(@PathVariable UUID id) {
        requestService.restore(id);
        return ResponseEntity.ok().build();
    }
}