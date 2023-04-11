package br.com.api.kalil_studio.URL_Shorter.utils;

import jakarta.persistence.criteria.CriteriaBuilder;

import java.beans.Encoder;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.stream.Stream;

public class ConvertBase64 {

    public static final Base64.Encoder ENCODER = Base64.getUrlEncoder().withoutPadding();
    public static final Base64.Decoder DECODER = Base64.getUrlDecoder();

    public static Long fromBase64(String base64Number) {
        long number = 0;
        for (byte b : DECODER.decode(base64Number)) {
            number <<= 8;
            number |= (b & 0xFF);
        }
        return number;
    }

    public static String toBase64(Long number) {
        return ENCODER.encodeToString(BigInteger.valueOf(number).toByteArray());
    }
}
