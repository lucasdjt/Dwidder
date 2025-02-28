package modele.dto;

import java.time.LocalDateTime;

public class Message {
    private int mid;
    private int cid;
    private int uid;
    private String contenu;
    private LocalDateTime dateMess;
    
    public Message(int mid, int cid, int uid, String contenu, LocalDateTime dateMess) {
        this.mid = mid;
        this.cid = cid;
        this.uid = uid;
        this.contenu = contenu;
        this.dateMess = dateMess;
    }

    @Override
    public String toString() {
        return "Message [mid=" + mid + ", cid=" + cid + ", uid=" + uid + ", contenu=" + contenu + ", dateMess="
                + dateMess + "]";
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

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public LocalDateTime getDateMess() {
        return dateMess;
    }

    public void setDateMess(LocalDateTime dateMess) {
        this.dateMess = dateMess;
    }
}
