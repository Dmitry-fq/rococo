package ru.rococo.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import ru.rococo.grpc.Artist;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "artist")
public class ArtistEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, columnDefinition = "UUID default gen_random_uuid()")
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column()
    private String biography;

    @Column(name = "photo", columnDefinition = "bytea")
    private byte[] photo;

    public Artist toArtist() {
        return Artist.newBuilder()
                     .setId(id.toString())
                     .setName(name)
                     .setBiography(biography != null ? biography : "")
                     .setPhoto(photo != null ? new String(photo, StandardCharsets.UTF_8) : "")
                     .build();
    }
}
