package modele.dto;

import java.time.LocalDateTime;

public class Membre {
    private int uid;
    private int gid;
    private LocalDateTime djoin;
    
    public Membre(int uid, int gid, LocalDateTime djoin) {
        this.uid = uid;
        this.gid = gid;
        this.djoin = djoin;
    }

    @Override
    public String toString() {
        return "Membre [uid=" + uid + ", gid=" + gid + ", djoin=" + djoin + "]";
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

    public LocalDateTime getDjoin() {
        return djoin;
    }

    public void setDjoin(LocalDateTime djoin) {
        this.djoin = djoin;
    }
}

