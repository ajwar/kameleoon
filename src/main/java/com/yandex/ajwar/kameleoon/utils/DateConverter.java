package com.yandex.ajwar.kameleoon.utils;

import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.TimeUnit;

@UtilityClass
public class DateConverter {

    /**
     * Converts LocalDateTime object into timeInMillis
     *
     * @param date Object to be converted
     * @return timeInMillis
     */
    public static long localDateTimeToMillis(LocalDateTime date) {
        return date.atZone(ZoneOffset.UTC).toInstant().toEpochMilli();
    }

    /**
     * Converts LocalDateTime object into microseconds
     *
     * @param date Object to be converted
     * @return timeInMicroseconds
     */
    public static long localDateTimeToMicro(LocalDateTime date) {
        final var instant = date.atZone(ZoneOffset.UTC).toInstant();
        return instant.getEpochSecond() * 1_000_000 + TimeUnit.MICROSECONDS.convert(instant.getNano(), TimeUnit.NANOSECONDS);
    }

    /**
     * Converts time in millis into LocalDateTime
     *
     * @param timeInMillis Object to be converted
     * @return LocalDateTime
     */
    public static LocalDateTime millisToLocalDateTime(long timeInMillis) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timeInMillis), ZoneOffset.UTC);
    }
}
