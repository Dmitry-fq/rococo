package ru.rococo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Size;
import ru.rococo.config.RococoGatewayServiceConfig;
import ru.rococo.grpc.UserResponse;

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
        @Size(max = RococoGatewayServiceConfig.ONE_MB)
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
