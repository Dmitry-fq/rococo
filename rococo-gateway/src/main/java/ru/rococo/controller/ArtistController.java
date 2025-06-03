package ru.rococo.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.rococo.config.RococoGatewayServiceConfig;
import ru.rococo.model.ArtistJson;
import ru.rococo.service.ArtistService;

@RestController
@RequestMapping("/api/artist")
@SecurityRequirement(name = RococoGatewayServiceConfig.OPEN_API_AUTH_SCHEME)
public class ArtistController {

    private final ArtistService artistService = new ArtistService();

    @GetMapping("/{id}")
    public ArtistJson getArtist(@PathVariable("id") String id) {
        return artistService.getArtist(id);
    }

    @GetMapping()
    public Page<ArtistJson> allArtists(@RequestParam(required = false) String name,
                                       @PageableDefault Pageable pageable
    ) {
        return artistService.allArtists(name, pageable);
    }

    @PostMapping()
    public ArtistJson addArtist(@Valid @RequestBody ArtistJson artist) {
        return artistService.addArtist(artist);
    }

    @PatchMapping()
    public ArtistJson updateArtist(@Valid @RequestBody ArtistJson artist) {
        return artistService.updateArtist(artist);
    }
}
