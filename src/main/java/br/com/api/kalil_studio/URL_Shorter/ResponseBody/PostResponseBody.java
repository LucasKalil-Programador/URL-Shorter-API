package br.com.api.kalil_studio.URL_Shorter.ResponseBody;

import br.com.api.kalil_studio.URL_Shorter.models.URLModel;

import java.time.LocalDateTime;

import static br.com.api.kalil_studio.URL_Shorter.utils.ConvertBase64.toBase64;

public class PostResponseBody {

    // https://www.google.com
    public final String originalURL;

    // https://example/AB89AS
    public final String shortPath;

    // maxAccess = 100
    public final String maxAccess;

    // 2023-04-08T15:37:22Z
    public final LocalDateTime expirationDateUTC;

    public PostResponseBody(URLModel url) {
        originalURL = url.getOriginalURL();
        shortPath = String.format("/%s", toBase64(url.getId()));
        maxAccess = url.getMaxAccess().toString();
        expirationDateUTC = url.getExpirationDateTime();
    }
}
