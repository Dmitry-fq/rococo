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
import ru.rococo.model.MuseumJson;
import ru.rococo.service.MuseumClient;

@RestController
@RequestMapping("/api/museum")
@SecurityRequirement(name = RococoGatewayServiceConfig.OPEN_API_AUTH_SCHEME)
public class MuseumController {

    private final MuseumClient museumClient;

    @Autowired
    public MuseumController(MuseumClient museumClient) {
        this.museumClient = museumClient;
    }

    @GetMapping("/{id}")
    public MuseumJson getMuseum(@PathVariable("id") String id) {
        return museumClient.getMuseum(id);
    }

    @GetMapping()
    public Page<MuseumJson> allMuseums(@RequestParam(required = false) String title,
                                       @PageableDefault Pageable pageable
    ) {
        String museumTitle = "";
        if (title != null) {
            museumTitle = title;
        }

        return museumClient.allMuseums(museumTitle, pageable);
    }

    @PatchMapping()
    public MuseumJson updateMuseum(@Valid @RequestBody MuseumJson museum) {
        return museumClient.updateMuseum(museum);
    }

    @PostMapping()
    public MuseumJson addMuseum(@Valid @RequestBody MuseumJson museum) {
        return museumClient.addMuseum(museum);
    }
}
