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
import ru.rococo.grpc.Museum;
import ru.rococo.grpc.Painting;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "painting")
public class PaintingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, columnDefinition = "UUID default gen_random_uuid()")
    private UUID id;

    @Column(name = "artist_id")
    private UUID artistId;

    @Column(name = "museum_id")
    private UUID museumId;

    @Column(name = "content", columnDefinition = "bytea")
    private byte[] content;

    @Column()
    private String title;

    @Column()
    private String description;

    public Painting toPainting(Artist artist, Museum museum) {
        return Painting.newBuilder()
                       .setId(id.toString())
                       .setArtist(artist)
                       .setContent(content != null ? new String(content, StandardCharsets.UTF_8) : "")
                       .setTitle(title != null ? title : "")
                       .setDescription(description != null ? description : "")
                       .setMuseum(museum)
                       .build();
    }
}
