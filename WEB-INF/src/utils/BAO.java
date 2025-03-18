package utils;

import java.sql.*;
import java.time.*;
import java.lang.reflect.Field;

// Boîte à outil permettant de convertisseur les SQL <--> TIME
public class BAO {

    public static Timestamp conversion(LocalDateTime ldt) { return ldt == null ? null : Timestamp.valueOf(ldt); }

    public static LocalDateTime conversion(Timestamp ts) { return ts == null ? null : ts.toLocalDateTime(); }

    public static Date conversion(LocalDate ld) { return ld == null ? null : Date.valueOf(ld); }

    public static LocalDate conversion(Date date) { return date == null ? null : date.toLocalDate(); }

    public static String escapeHTML(String input) { 
        if (input == null) { return null; } 
        return input.replace("&", "&amp;") 
                    .replace("<", "&lt;") 
                    .replace(">", "&gt;") 
                    .replace("\"", "&quot;") 
                    .replace("'", "&#x27;"); 
    }
    
    public static String toJson(Object object) throws IllegalAccessException {
        if (object == null) return "null";
    
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{");
        Field[] fields = object.getClass().getDeclaredFields();
    
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            jsonBuilder.append("\"").append(field.getName()).append("\":");
    
            Object value = field.get(object);
            if (value == null) {
                jsonBuilder.append("null");
            } else {
                jsonBuilder.append("\"").append(value).append("\"");
            }
    
            if (i < fields.length - 1) {
                jsonBuilder.append(",");
            }
        }
        jsonBuilder.append("}");
        return jsonBuilder.toString();
    }    
}
