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
import ru.rococo.model.PaintingJson;
import ru.rococo.service.PaintingService;

@RestController
@RequestMapping("/api/painting")
@SecurityRequirement(name = RococoGatewayServiceConfig.OPEN_API_AUTH_SCHEME)
public class PaintingController {

    private final PaintingService paintingService = new PaintingService();

    @GetMapping("/{id}")
    public PaintingJson getPainting(@PathVariable("id") String id) {
        return paintingService.getPainting(id);
    }

    @GetMapping("/author/{authorId}")
    public Page<PaintingJson> getPaintingByAuthorId(@PathVariable("authorId") String authorId,
                                                    @RequestParam(required = false) String title,
                                                    @PageableDefault Pageable pageable) {
        return paintingService.getPaintingByAuthorId(authorId, title, pageable);
    }

    @GetMapping()
    public Page<PaintingJson> allPaintings(@RequestParam(required = false) String title,
                                           @PageableDefault Pageable pageable
    ) {
        return paintingService.allPaintings(title, pageable);
    }

    @PostMapping()
    public PaintingJson addPainting(@Valid @RequestBody PaintingJson painting) {
        return paintingService.addPainting(painting);
    }

    @PatchMapping()
    public PaintingJson updatePainting(@Valid @RequestBody PaintingJson painting) {
        return paintingService.updatePainting(painting);
    }
}
