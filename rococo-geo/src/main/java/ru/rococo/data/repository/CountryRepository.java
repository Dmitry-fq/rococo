package ru.rococo.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rococo.data.CountryEntity;

import java.util.UUID;

public interface CountryRepository extends JpaRepository<CountryEntity, UUID> {
}
