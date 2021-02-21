package org.learn.something.security.crypto;

import lombok.SneakyThrows;

import javax.crypto.Cipher;

public class SecretKeyMain {

    @SneakyThrows
    public static void main(String[] args) {
        Cipher cipher = Cipher.getInstance("AES_128");
    }

}
