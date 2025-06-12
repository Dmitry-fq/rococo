package ru.rococo.data.repository;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.rococo.data.UserEntity;

import java.util.Optional;
import java.util.UUID;

public interface UserdataRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByUsername(@Nonnull String username);
}
