package modele.utils;

import java.sql.*;
import java.time.*;

// Boîte à outil permettant de convertisseur les SQL <--> TIME
public class BAO {

    public static Timestamp conversion(LocalDateTime ldt) { return ldt == null ? null : Timestamp.valueOf(ldt); }

    public static LocalDateTime conversion(Timestamp ts) { return ts == null ? null : ts.toLocalDateTime(); }

    public static Date conversion(LocalDate ld) { return ld == null ? null : Date.valueOf(ld); }

    public static LocalDate conversion(Date date) { return date == null ? null : date.toLocalDate(); }

    public static Duration conversionIntervalToDuration(String interval) {
        boolean isNegative = interval.startsWith("-");
        if(isNegative) interval = interval.substring(1).trim();
        String[] parts = interval.split(" ");
        long days = 0;
        long hours = 0;
        long minutes = 0;
        long seconds = 0;
        for (int i = 0; i < parts.length; i++) {
            try {
                if (parts[i].contains("day")) days = Long.parseLong(parts[i - 1]);
                else if (parts[i].contains("hour")) hours = Long.parseLong(parts[i - 1]);
                else if (parts[i].contains("minute")) minutes = Long.parseLong(parts[i - 1]);
                else if (parts[i].contains("second")) seconds = Long.parseLong(parts[i - 1]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        Duration duration = Duration.ofDays(days).plusHours(hours).plusMinutes(minutes).plusSeconds(seconds);

        if (isNegative) duration = duration.negated();
        return duration;
    }

    public static String conversionDurationToInterval(Duration duration) {
        long days = duration.toDays();
        long hours = duration.toHoursPart();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();
        StringBuilder interval = new StringBuilder();

        if (days != 0) {
            interval.append(days).append(" day");
            if (days > 1) interval.append("s");
            interval.append(" ");
        }
        if (hours != 0) {
            interval.append(hours).append(" hour");
            if (hours > 1) interval.append("s");
            interval.append(" ");
        }
        if (minutes != 0) {
            interval.append(minutes).append(" minute");
            if (minutes > 1) interval.append("s");
            interval.append(" ");
        }
        if (seconds != 0) {
            interval.append(seconds).append(" second");
            if (seconds > 1) interval.append("s");
            interval.append(" ");
        }
        return interval.toString().trim();
    }
}
