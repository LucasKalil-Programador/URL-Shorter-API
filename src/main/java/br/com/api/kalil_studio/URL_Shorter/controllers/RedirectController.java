package br.com.api.kalil_studio.URL_Shorter.controllers;

import br.com.api.kalil_studio.URL_Shorter.models.URLModel;
import br.com.api.kalil_studio.URL_Shorter.services.URLService;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import static br.com.api.kalil_studio.URL_Shorter.utils.ConvertBase64.fromBase64;
import static br.com.api.kalil_studio.URL_Shorter.utils.validURL.validURL;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class RedirectController {
    private static final RedirectView DEFAULT_REDIRECT = new RedirectView("https://www.google.com.br");

    final URLService urlService;

    public RedirectController(URLService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/{base64ID}")
    public RedirectView getAllURL(@PathVariable(value = "base64ID") String base64ID) {
        Long id;
        try {
            id = fromBase64(base64ID);
        } catch (Exception e) { return DEFAULT_REDIRECT; }

        return urlService.find(id)
                .map(urlModel -> {
                    if(validURL(urlModel)) {
                        urlModel.setAccessCount(urlModel.getAccessCount() + 1);
                        urlModel = urlService.save(urlModel);
                        return new RedirectView(urlModel.getOriginalURL());
                    }
                    return DEFAULT_REDIRECT;
                }).orElseGet(() -> DEFAULT_REDIRECT);
    }
}
