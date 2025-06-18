package ru.rococo.data.repository;

import jakarta.annotation.Nonnull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.rococo.data.ArtistEntity;

import java.util.UUID;

public interface ArtistRepository extends JpaRepository<ArtistEntity, UUID> {

    @Nonnull
    Page<ArtistEntity> findAllByNameContainingIgnoreCase(@Nonnull String name, @Nonnull Pageable pageable);
}
