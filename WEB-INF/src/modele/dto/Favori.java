package modele.dto;

import java.time.LocalDateTime;

public class Favori {
    private int uid;
    private int pid;
    private LocalDateTime dateFavori;
    
    public Favori(int uid, int pid, LocalDateTime dateFavori) {
        this.uid = uid;
        this.pid = pid;
        this.dateFavori = dateFavori;
    }

    @Override
    public String toString() {
        return "Favori [uid=" + uid + ", pid=" + pid + ", dateFavori=" + dateFavori + "]";
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public LocalDateTime getDateFavori() {
        return dateFavori;
    }

    public void setDateFavori(LocalDateTime dateFavori) {
        this.dateFavori = dateFavori;
    }
}
