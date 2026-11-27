package ru.mirea.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.security.domain.RequestNote;
import java.util.List;
import java.util.UUID;

public interface RequestNoteRepository extends JpaRepository<RequestNote, UUID> {
    List<RequestNote> findByRequestIdOrderByPinnedDescCreatedAtDesc(UUID requestId);
}