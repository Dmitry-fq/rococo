package ru.rococo.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rococo.config.RococoGatewayServiceConfig;
import ru.rococo.model.UserJson;
import ru.rococo.service.UserdataClient;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = RococoGatewayServiceConfig.OPEN_API_AUTH_SCHEME)
public class UserdataController {

    private final UserdataClient userdataClient;

    @Autowired
    public UserdataController(UserdataClient userdataClient) {
        this.userdataClient = userdataClient;
    }

    @GetMapping("/user")
    public UserJson getUser(@AuthenticationPrincipal Jwt principal) {
        String username = principal.getClaim("sub");
        return userdataClient.getUser(username);
    }

    @PatchMapping("/user")
    public UserJson updateUser(@Valid @RequestBody UserJson updatedUser) {
        return userdataClient.updateUser(updatedUser);
    }
}
