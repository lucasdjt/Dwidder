package modele.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import utils.BAO;

public class Message {
    private int mid;
    private int uidEnvoyeur;
    private int uidReceveur;
    private String corps;
    private String imgMess;
    private LocalDateTime dmess;
    
    public Message(int mid, int uidEnvoyeur, int uidReceveur, String corps, String imgMess, LocalDateTime dmess) {
        this.mid = mid;
        this.uidEnvoyeur = uidEnvoyeur;
        this.uidReceveur = uidReceveur;
        this.corps = corps;
        this.imgMess = imgMess;
        this.dmess = dmess;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public int getUidEnvoyeur() {
        return uidEnvoyeur;
    }

    public void setUidEnvoyeur(int uidEnvoyeur) {
        this.uidEnvoyeur = uidEnvoyeur;
    }

    public int getUidReceveur() {
        return uidReceveur;
    }

    public void setUidReceveur(int uidReceveur) {
        this.uidReceveur = uidReceveur;
    }

    public String getCorps() {
        return BAO.escapeHTML(corps);
    }

    public String getHTMLCorps() {
        return corps;
    }

    public void setCorps(String corps) {
        this.corps = corps;
    }

    public String getImgMess() {
        return imgMess;
    }

    public void setImgMess(String imgMess) {
        this.imgMess = imgMess;
    }

    public LocalDateTime getDmess() {
        return dmess;
    }

    public String getDmessAsDate() {
        return dmess.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    public void setDmess(LocalDateTime dmess) {
        this.dmess = dmess;
    }
}
