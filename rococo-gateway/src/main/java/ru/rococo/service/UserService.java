package ru.rococo.service;

import org.springframework.stereotype.Component;
import ru.rococo.model.UserJson;

import javax.annotation.Nonnull;
import java.util.UUID;

@Component
public class UserService {

    public @Nonnull UserJson getUser(@Nonnull String username) {
        //TODO намеренно хардкожу юзера для монолита
        return new UserJson(
                UUID.randomUUID(),
                "username",
                "firstname",
                "surname",
                "123"
        );
    }

    public @Nonnull UserJson updateUser(@Nonnull String username, @Nonnull UserJson updatedUser) {
        //TODO намеренно хардкожу юзера для монолита
        return updatedUser;
    }
}
