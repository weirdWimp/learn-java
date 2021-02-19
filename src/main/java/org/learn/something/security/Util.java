package org.learn.something.security;

import java.util.Base64;

public class Util {

    public static final char[] hexChars = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static String toHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            int high = (b & 0xf0) >> 4;
            int low = (b & 0x0f);
            builder.append(hexChars[high]).append(hexChars[low]);
        }
        return builder.toString();
    }

    public static String base64Encode(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }
}
