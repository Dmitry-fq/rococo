package ru.rococo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.rococo.grpc.UserResponse;

import javax.annotation.Nonnull;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserJson(
        @JsonProperty("id")
        UUID id,
        @JsonProperty("username")
        String username,

        @JsonProperty("firstname")
        String firstname,

        @JsonProperty("lastname")
        String lastname,

        @JsonProperty("avatar")
        String avatar
) {
    public static @Nonnull UserJson fromUserResponse(@Nonnull UserResponse userResponse) {
        return new UserJson(
                UUID.fromString(userResponse.getId()),
                userResponse.getUsername(),
                userResponse.getFirstname(),
                userResponse.getLastname(),
                userResponse.getAvatar()
        );
    }
}
