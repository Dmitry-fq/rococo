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
import ru.rococo.model.PaintingJson;
import ru.rococo.service.PaintingClient;

@RestController
@RequestMapping("/api/painting")
@SecurityRequirement(name = RococoGatewayServiceConfig.OPEN_API_AUTH_SCHEME)
public class PaintingController {

    private final PaintingClient paintingClient;

    @Autowired
    public PaintingController(PaintingClient paintingClient) {
        this.paintingClient = paintingClient;
    }

    @GetMapping("/{id}")
    public PaintingJson getPainting(@PathVariable("id") String id) {
        return paintingClient.getPainting(id);
    }

    @GetMapping()
    public Page<PaintingJson> allPaintings(@RequestParam(required = false) String title,
                                           @PageableDefault Pageable pageable
    ) {
        String paintingTitle = "";
        if (title != null) {
            paintingTitle = title;
        }

        return paintingClient.allPaintings(paintingTitle, pageable);
    }

    @GetMapping("/author/{authorId}")
    public Page<PaintingJson> getPaintingsByAuthorId(@PathVariable("authorId") String authorId,
                                                     @PageableDefault Pageable pageable) {
        String updatedAuthorId = "";
        if (authorId != null) {
            updatedAuthorId = authorId;
        }

        return paintingClient.getPaintingsByAuthorId(updatedAuthorId, pageable);
    }

    @PostMapping()
    public PaintingJson addPainting(@Valid @RequestBody PaintingJson painting) {
        return paintingClient.addPainting(painting);
    }

    @PatchMapping()
    public PaintingJson updatePainting(@Valid @RequestBody PaintingJson painting) {
        return paintingClient.updatePainting(painting);
    }
}
