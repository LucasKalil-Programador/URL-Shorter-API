package br.com.api.kalil_studio.URL_Shorter.controllers;

import br.com.api.kalil_studio.URL_Shorter.ResponseBody.GetInfoResponseBody;
import br.com.api.kalil_studio.URL_Shorter.services.URLService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import static br.com.api.kalil_studio.URL_Shorter.utils.ConvertBase64.fromBase64;
import static br.com.api.kalil_studio.URL_Shorter.utils.ValidURL.validURL;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/info")
public class InfoController {
    final URLService urlService;


    public InfoController(URLService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/{base64ID}")
    public ResponseEntity<Object> getInfoEndPoint(@PathVariable(value = "base64ID") String base64ID) {
        Long id;
        try {
            id = fromBase64(base64ID);
        } catch (Exception e) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(String.format("The id: %s is wrong", base64ID)); }

        return urlService.find(id)
                .map(urlModel -> ResponseEntity.status(HttpStatus.OK)
                        .body((Object) new GetInfoResponseBody(urlModel))

                ).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(String.format("The id: %s not exist", base64ID))
                );
    }
}
