package ru.rococo.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import ru.rococo.grpc.UserResponse;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "\"users\"")
public class UserEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, columnDefinition = "UUID default gen_random_uuid()")
    private UUID id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column()
    private String firstname;

    @Column()
    private String lastname;

    @Column(name = "avatar", columnDefinition = "bytea")
    private byte[] avatar;

    public UserResponse toUserResponse() {
        return UserResponse.newBuilder()
                           .setId(id.toString())
                           .setUsername(username)
                           .setFirstname(firstname != null ? firstname : "")
                           .setLastname(lastname != null ? lastname : "")
                           .setAvatar(avatar != null ? new String(avatar, StandardCharsets.UTF_8) : "")
                           .build();
    }
}
