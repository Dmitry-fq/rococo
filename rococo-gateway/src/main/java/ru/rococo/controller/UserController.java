package ru.rococo.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rococo.config.RococoGatewayServiceConfig;
import ru.rococo.model.UserJson;
import ru.rococo.service.UserService;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = RococoGatewayServiceConfig.OPEN_API_AUTH_SCHEME)
public class UserController {

    private final UserService userService = new UserService();

    @GetMapping("/user")
    public UserJson getUser(@AuthenticationPrincipal Jwt principal) {
        String username = principal.getClaim("sub");
        return userService.getUser(username);
    }

    @PatchMapping("/user")
    public UserJson updateUser(@AuthenticationPrincipal Jwt principal, UserJson updatedUser) {
        String username = principal.getClaim("sub");
        return userService.updateUser(username, updatedUser);
    }
}
