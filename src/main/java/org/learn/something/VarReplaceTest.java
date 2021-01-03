package org.learn.something;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VarReplaceTest {

    public static final Pattern VAR_PATTERN = Pattern.compile("\\$\\{([^{}]+)}");

    public static void main(String[] args) {
        String template = "This is ${var1}. And This is ${var2}, and this is ${var3}";
        String result = varReplace(template);
        System.out.println(result);
    }

    public static String varReplace(String template) {
        StringBuffer result = new StringBuffer();
        Matcher matcher = VAR_PATTERN.matcher(template);

        while (matcher.find()) {
            System.out.println("variable: " + matcher.group());
            String var = matcher.group(1);
            String replaceValue;
            switch (var) {
                case "var1":
                    replaceValue = "love";
                    break;
                case "var2":
                    replaceValue = "hate";
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported Variable:" + var);
            }
            matcher.appendReplacement(result, replaceValue);
        }

        matcher.appendTail(result);
        return result.toString();
    }
}
