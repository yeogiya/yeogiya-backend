package com.yeogiya.web.config;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JasyptConfigTest {

    @Test
    public void jasypt_암복호화_테스트() {
        String plainText = "Vwgff4uvzQ4pes0Zt7sDNtL6pxGIkg2k95ZIrVhvlGmxcDRq9O1fnLN2lEzItsNE4w_lQ3f7xd09ukYxzIYS6InrYfg4ed2BSP0wmZ2RJEswzDsCLNqwRRXW780o1TYbuQpiXuUN0TnwXzb2l4YnNcXLHyBBJoIU17y1H56Aq1-ABW6MREvcFvlW-oUcMw92R0piQK4hO_Xo8AFIDnbeAqQUQ2Q91iQZRTtiNrV9Gv_pF_f1LF9OLDnvmTTy7Av7yFRstie90G9ABYsFTrxywHLzA-QMDYOeUOk8wq6TfxKbULK8PqWR__s1ebFlA3bFO1shhUdffA";

        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();

        config.setPassword("yeogiya-2023-encryption!");
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize(2);
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.NoIvGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);

        String encryptedText = encryptor.encrypt(plainText);
        System.out.println(encryptedText);
        String decryptedText = encryptor.decrypt(encryptedText);
        System.out.println(decryptedText);

        assertEquals(decryptedText, plainText);
    }
}