package org.learn.something.date;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.Objects;
import java.util.TimeZone;
import java.util.function.UnaryOperator;

public class DateTimeAPI {

    public static void main(String[] args) {
        // gap();
        // accessAndWrite();
        // formatAndParse();
        zonedDateTime();
    }

    private static void gap() {
        LocalTime time1 = LocalTime.of(1, 50, 10);
        LocalTime time2 = LocalTime.of(2, 50, 10);
        System.out.println(Duration.between(time1, time2).getSeconds());

        LocalDateTime dateTime1 = LocalDateTime.of(2020, 10, 1, 1, 50, 10);
        LocalDateTime dateTime2 = LocalDateTime.of(2020, 10, 2, 1, 50, 10);
        System.out.println(Duration.between(dateTime1, dateTime2).getSeconds());

        LocalDate localDate1 = LocalDate.of(2020, 11, 1);
        LocalDate localDate2 = LocalDate.of(2021, 10, 1);
        Period period = Period.between(localDate1, localDate2);
        System.out.println(period);
    }

    private static void accessAndWrite() {
        LocalTime time = LocalTime.of(1, 50, 10);
        System.out.println(time.get(ChronoField.HOUR_OF_DAY));

        LocalTime time2 = time.with(ChronoField.HOUR_OF_DAY, 23);
        System.out.println(time2);

        LocalDate now = LocalDate.now();
        LocalDate withDate = now.with(TemporalAdjusters.dayOfWeekInMonth(1, DayOfWeek.MONDAY));

        System.out.println(now.adjustInto(LocalDate.of(2020, 10, 1)));

    }

    private static void zonedDateTime() {
        Instant instant = Instant.now();
        ZonedDateTime zonedDateTime1 = instant.atZone(ZoneId.systemDefault());

        // Arrays.stream(TimeZone.getAvailableIDs()).forEach(System.out::println);
        ZoneId shangHai = TimeZone.getTimeZone("Asia/Shanghai").toZoneId();

        LocalDateTime dateTime = LocalDateTime.now();

        ZoneId romeId = ZoneId.of("Europe/Rome");
        ZonedDateTime zonedDateTime2 = dateTime.atZone(romeId);

        ZoneOffset zoneOffset = ZoneOffset.ofHours(-5);
        OffsetDateTime offsetDateTime = OffsetDateTime.of(dateTime, zoneOffset);

        System.out.println(offsetDateTime);

    }

    /**
     * LocalDate本身 实现了 TemporalAdjuster 接口： Adjusts the specified temporal object to have the same date as this object
     */
    public static TemporalAdjuster ofDateAdjuster(UnaryOperator<LocalDate> dateBasedAdjuster) {
        return (temporal) -> {
            LocalDate input = LocalDate.from(temporal);
            LocalDate output = dateBasedAdjuster.apply(input);
            return temporal.with(output);
        };
    }

    private static void formatAndParse() {
        System.out.println(LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE));
    }

}
