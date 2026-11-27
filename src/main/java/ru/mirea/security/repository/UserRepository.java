package ru.mirea.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.security.domain.UserAccount;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserAccount, UUID> {
    Optional<UserAccount> findByUsername(String username);
}
