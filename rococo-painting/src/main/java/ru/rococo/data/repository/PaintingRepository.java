package ru.rococo.data.repository;

import jakarta.annotation.Nonnull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.rococo.data.PaintingEntity;

import java.util.UUID;

public interface PaintingRepository extends JpaRepository<PaintingEntity, UUID> {

    @Nonnull
    Page<PaintingEntity> findAllByTitleContainingIgnoreCase(@Nonnull String title, @Nonnull Pageable pageable);

    @Nonnull
    Page<PaintingEntity> findAllByArtistId(@Nonnull UUID id, @Nonnull Pageable pageable);
}
