package ru.rococo.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rococo.data.CityEntity;

import java.util.UUID;

public interface CityRepository extends JpaRepository<CityEntity, UUID> {
}
