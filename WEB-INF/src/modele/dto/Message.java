package modele.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message {
    private int mid;
    private int cid;
    private int uid;
    private String corps;
    private LocalDateTime dmess;
    
    public Message(int mid, int cid, int uid, String corps, LocalDateTime dmess) {
        this.mid = mid;
        this.cid = cid;
        this.uid = uid;
        this.corps = corps;
        this.dmess = dmess;
    }

    public Message() {
        corps = null;
        dmess = null;
    }

    @Override
    public String toString() {
        return "Message [mid=" + mid + ", cid=" + cid + ", uid=" + uid + ", corps=" + corps + ", dmess=" + dmess + "]";
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getCorps() {
        return corps;
    }

    public void setCorps(String corps) {
        this.corps = corps;
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
