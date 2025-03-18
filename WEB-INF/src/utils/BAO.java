package utils;

import java.sql.*;
import java.time.*;

import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;

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
    
    public static String getRepertory(){
        return "/WEB-INF/vue/";
    }

        
    public static String uploadImage(Part filePart, String servPath) throws IOException {
        String UPLOAD_DIR = "img";
        String SEP = "/";
        if (filePart == null || servPath == null || filePart.getSubmittedFileName() == null) {
            throw new IllegalArgumentException("Invalid filePart or servPath");
        }
    
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        fileName = fileName.replaceAll("[^a-zA-Z0-9._-]", "_");
        String pdp = null;
        if (fileName != null && !fileName.isEmpty()) {
            String uploadPath = servPath + SEP + UPLOAD_DIR;
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();
            
            File file = new File(uploadPath + SEP + fileName);
            String baseName = fileName.substring(0, fileName.lastIndexOf('.'));
            String extension = fileName.substring(fileName.lastIndexOf('.'));
            int counter = 1;
            while (file.exists()) {
            fileName = baseName + "_" + counter + extension;
            file = new File(uploadPath + SEP + fileName);
            counter++;
            }
            pdp = UPLOAD_DIR + SEP + fileName;
            try (InputStream fileContent = filePart.getInputStream()) {
            Files.copy(fileContent, file.toPath());
            }
        }
        return pdp;
    }

    public static String cleanReferer(String referer) {
        if (referer != null && referer.contains("?")) {
            referer = referer.substring(0, referer.indexOf("?"));
        }
        return referer;
    }
}
