package br.com.api.kalil_studio.URL_Shorter.ResponseBody;

import br.com.api.kalil_studio.URL_Shorter.models.URLModel;
import br.com.api.kalil_studio.URL_Shorter.utils.ConvertBase64;

import java.time.LocalDateTime;

public class GetInfoResponseBody {
    // https://www.google.com
    public final String originalURL;

    // https://example/AB89AS
    public final String shortPath;

    public final LocalDateTime expirationDateTime;

    public final Integer maxAccess;

    public final Integer accessCount;

    public GetInfoResponseBody(URLModel url) {
        this.originalURL = url.getOriginalURL();
        this.shortPath = ConvertBase64.toBase64(url.getId());
        this.expirationDateTime = url.getExpirationDateTime();
        this.maxAccess = url.getMaxAccess();
        this.accessCount = url.getAccessCount();
    }
}
