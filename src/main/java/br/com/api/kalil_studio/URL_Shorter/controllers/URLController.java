package br.com.api.kalil_studio.URL_Shorter.controllers;

import br.com.api.kalil_studio.URL_Shorter.ResponseBody.PostResponseBody;
import br.com.api.kalil_studio.URL_Shorter.dtos.URLDTO;
import br.com.api.kalil_studio.URL_Shorter.models.URLModel;
import br.com.api.kalil_studio.URL_Shorter.services.URLService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/url")
public class URLController {
    final URLService urlService;

    public URLController(URLService urlService) {
        this.urlService = urlService;
    }

    @PostMapping
    public ResponseEntity<Object> shorterURL(
            @RequestBody @Valid URLDTO urldto,
            @RequestParam(name = "minutes", required = false, defaultValue = "-1") Integer vMinutes,
            @RequestParam(name = "max_access", required = false, defaultValue = "-1") Integer maxAcs) {
        // defaultValue of validity_minutes = -1 unlimited
        // defaultValue of max_access = -1 = unlimited

        ResponseEntity<Object> BAD_REQUEST = getObjectResponseEntity(vMinutes, maxAcs);
        if (BAD_REQUEST != null) return BAD_REQUEST;

        if(vMinutes != -1 || maxAcs != -1 || !urlService.existsByOriginalURL(urldto.getOriginalURL())) {
            URLModel urlModel = getUrlModel(urldto, vMinutes, maxAcs);
            var responseBody = new PostResponseBody(urlService.save(urlModel));
            return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
        } else {
            return urlService.findByOriginalURL(urldto.getOriginalURL())
                    .map(urlModel -> ResponseEntity.status(HttpStatus.OK).body((Object) new PostResponseBody(urlModel)))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal error"));
        }
    }

    private static ResponseEntity<Object> getObjectResponseEntity(Integer vMinutes, Integer maxAcs) {
        if((vMinutes <= 0 && vMinutes != -1) || vMinutes > 2880) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(String.format("invalid value: '%s' (1 <= validity_minutes <= 2880 or validity_minutes = -1 = unlimited)", vMinutes));
        }
        if(maxAcs <= 0 && maxAcs != -1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(String.format("invalid value: '%s' (1 <= max_access <= 2147483647 or max_access = -1 = unlimited)", maxAcs));
        }
        return null;
    }

    private static URLModel getUrlModel(URLDTO urldto, Integer vMinutes, Integer maxAcs) {
        URLModel urlModel = new URLModel();
        BeanUtils.copyProperties(urldto, urlModel);
        urlModel.setRegistrationDateTime(LocalDateTime.now(ZoneId.of("UTC")));
        urlModel.setAccessCount(0);

        urlModel.setExpirationDateTime(vMinutes != -1? urlModel.getRegistrationDateTime().plusMinutes(vMinutes): urlModel.getRegistrationDateTime());
        urlModel.setMaxAccess(maxAcs);
        return urlModel;
    }
}
