package br.com.api.kalil_studio.URL_Shorter.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

public class URLDTO {

    @URL
    @NotBlank
    @NotNull
    @Size(min = 0, max = 4096)
    private String originalURL;

    public String getOriginalURL() {
        return originalURL;
    }

    public void setOriginalURL(String originalURL) {
        this.originalURL = originalURL;
    }
}
