package org.learn.something.security.mac;

import lombok.SneakyThrows;
import org.apache.tomcat.util.buf.HexUtils;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

public class MACTest {

    /**
     * MAC Message Authentication Code 消息验证码
     */
    @SneakyThrows
    public static void main(String[] args) {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacMD5");
        SecretKey key = keyGenerator.generateKey();

        byte[] keyBytes = key.getEncoded();
        System.out.println("key:" + HexUtils.toHexString(keyBytes));

        Mac hmacMD5 = Mac.getInstance("HmacMD5");
        hmacMD5.init(key);

        byte[] macValue = hmacMD5.doFinal("hello world".getBytes(StandardCharsets.UTF_8));
        System.out.println("mac: " + HexUtils.toHexString(macValue));
    }

}
