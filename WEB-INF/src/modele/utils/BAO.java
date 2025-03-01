package modele.utils;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

// Boîte à outil permettant de convertisseur les SQL <--> TIME
public class BAO {

    public static Timestamp conversion(LocalDateTime ldt) { return ldt == null ? null : Timestamp.valueOf(ldt); }

    public static LocalDateTime conversion(Timestamp ts) { return ts == null ? null : ts.toLocalDateTime(); }

    public static Date conversion(LocalDate ld) { return ld == null ? null : Date.valueOf(ld); }

    public static LocalDate conversion(Date date) { return date == null ? null : date.toLocalDate(); }

}
