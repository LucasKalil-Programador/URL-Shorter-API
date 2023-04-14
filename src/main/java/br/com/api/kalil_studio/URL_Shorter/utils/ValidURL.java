package br.com.api.kalil_studio.URL_Shorter.utils;

import br.com.api.kalil_studio.URL_Shorter.models.URLModel;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class ValidURL {

    public static boolean validURL(URLModel urlModel) {
        if(urlModel.getMaxAccess() != -1 && urlModel.getAccessCount() >= urlModel.getMaxAccess()) {
            return false;
        }

        if(!urlModel.getRegistrationDateTime().equals(urlModel.getExpirationDateTime())) {
            var now = LocalDateTime.now(ZoneId.of("UTC"));
            return !now.isAfter(urlModel.getExpirationDateTime());
        }
        return true;
    }
}
