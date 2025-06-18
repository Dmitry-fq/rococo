package ru.rococo.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rococo.data.MuseumEntity;

import java.util.UUID;

public interface MuseumRepository extends JpaRepository<MuseumEntity, UUID> {
}
