package ru.rococo.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
import ru.rococo.service.ArtistClient;

@RestController
@RequestMapping("/api/artist")
@SecurityRequirement(name = RococoGatewayServiceConfig.OPEN_API_AUTH_SCHEME)
public class ArtistController {

    private final ArtistClient artistClient;

    @Autowired
    public ArtistController(ArtistClient artistClient) {
        this.artistClient = artistClient;
    }

    @GetMapping("/{id}")
    public ArtistJson getArtist(@PathVariable("id") String id) {
        return artistClient.getArtist(id);
    }

    @GetMapping()
    public Page<ArtistJson> allArtists(@RequestParam(required = false) String name,
                                       @PageableDefault Pageable pageable
    ) {
        String artistName = "";
        if (name != null) {
            artistName = name;
        }

        return artistClient.allArtists(artistName, pageable);
    }

    @PatchMapping()
    public ArtistJson updateArtist(@Valid @RequestBody ArtistJson artist) {
        return artistClient.updateArtist(artist);
    }

    @PostMapping()
    public ArtistJson addArtist(@Valid @RequestBody ArtistJson artist) {
        return artistClient.addArtist(artist);
    }
}
