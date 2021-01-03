package org.learn.something.format;

import java.text.ChoiceFormat;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class MessageFormatTest {

    public static void main(String[] args) {
        LocalDate parse = LocalDate.parse("20211101", DateTimeFormatter.ofPattern("yyyyMMdd"));

        System.out.println(parse.format(DateTimeFormatter.ofPattern("d")));
    }


}
