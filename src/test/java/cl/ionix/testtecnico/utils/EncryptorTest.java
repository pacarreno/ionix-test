package cl.ionix.testtecnico.utils;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EncryptorTest {

    Encryptor encryptor;

    @BeforeEach
    public void setUp(){
        encryptor = new Encryptor();
        encryptor.apiKeY = "ionix123456";
    }

    @Test
    public void debe_encriptar_parametro() {
        // rut de prueba
        String plainRut = "1-9";
        String result;
        try {
            result = encryptor.encrypt(plainRut);
            System.out.println("la encriptación de "+plainRut+" es "+result);
        } catch (Exception e) {
            e.printStackTrace();
            //falla la ejecución
            fail();
        }

    }
    
}