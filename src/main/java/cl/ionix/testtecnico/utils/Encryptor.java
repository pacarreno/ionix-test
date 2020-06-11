package cl.ionix.testtecnico.utils;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Encryptor {

    @Value("${ionix.api-key}")
    String apiKeY;

    /**
     * Permite encriptar un texto con DES
     * @param plainRut
     * @return
     * @throws Exception
     */
    public String encrypt(String plainRut) throws Exception {
        DESKeySpec keySpec = new DESKeySpec( apiKeY.getBytes( "UTF8" ));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance ( "DES" );
        SecretKey desKey = keyFactory.generateSecret(keySpec);

        byte [] cleartext = plainRut.getBytes( "UTF8" );
        Cipher cipher = Cipher.getInstance ( "DES" );
        cipher.init(Cipher.ENCRYPT_MODE, desKey);

        return Base64.getEncoder().encodeToString(cipher.doFinal(cleartext));
    }
}