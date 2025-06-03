package ru.rococo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rococo.model.SessionJson;

import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping("/api/session")
public class SessionController {

    @GetMapping()
    public SessionJson session(@AuthenticationPrincipal Jwt principal) {
        if (principal != null) {
            return new SessionJson(
                    principal.getClaim("sub"),
                    Date.from(Objects.requireNonNull(principal.getIssuedAt())),
                    Date.from(Objects.requireNonNull(principal.getExpiresAt()))
            );
        } else {
            return SessionJson.empty();
        }
    }
}
