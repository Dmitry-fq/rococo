package ru.rococo.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import ru.rococo.grpc.Country;
import ru.rococo.grpc.Geo;
import ru.rococo.grpc.Museum;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "museum")
public class MuseumEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, columnDefinition = "UUID default gen_random_uuid()")
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column()
    private String description;

    @Column(name = "photo", columnDefinition = "bytea")
    private byte[] photo;

    @Column(name = "country_id")
    private UUID countryId;

    @Column()
    private String city;

    public Museum toMuseum(String country) {
        return Museum.newBuilder()
                     .setId(id.toString())
                     .setTitle(title != null ? title : "")
                     .setDescription(description != null ? description : "")
                     .setPhoto(photo != null ? new String(photo, StandardCharsets.UTF_8) : "")
                     .setGeo(Geo.newBuilder()
                                .setCountry(Country.newBuilder()
                                                   .setId(String.valueOf(countryId))
                                                   .setName(country)
                                                   .build())
                                .setCity(city)
                                .build())
                     .build();
    }
}
