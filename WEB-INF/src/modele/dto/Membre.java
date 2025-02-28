package modele.dto;

import java.time.LocalDateTime;

public class Membre {
    private int uid;
    private int gid;
    private LocalDateTime dateJoin;
    
    public Membre(int uid, int gid, LocalDateTime dateJoin) {
        this.uid = uid;
        this.gid = gid;
        this.dateJoin = dateJoin;
    }

    @Override
    public String toString() {
        return "Membre [uid=" + uid + ", gid=" + gid + ", dateJoin=" + dateJoin + "]";
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public LocalDateTime getDateJoin() {
        return dateJoin;
    }

    public void setDateJoin(LocalDateTime dateJoin) {
        this.dateJoin = dateJoin;
    }
}

