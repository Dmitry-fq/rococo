package ru.rococo.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rococo.config.RococoGatewayServiceConfig;
import ru.rococo.model.CountryJson;
import ru.rococo.service.GeoService;

@RestController
@RequestMapping("/api/country")
@SecurityRequirement(name = RococoGatewayServiceConfig.OPEN_API_AUTH_SCHEME)
public class GeoController {

    private final GeoService geoService = new GeoService();

    @GetMapping()
    public Page<CountryJson> allCountries(@PageableDefault Pageable pageable
    ) {
        return geoService.allCountries(pageable);
    }
}
