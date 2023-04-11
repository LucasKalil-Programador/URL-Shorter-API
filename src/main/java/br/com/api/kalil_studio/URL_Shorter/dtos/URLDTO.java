package br.com.api.kalil_studio.URL_Shorter.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;

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
