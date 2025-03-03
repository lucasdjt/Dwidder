package modele.dto;

import java.time.LocalDateTime;

public class Favori {
    private int uid;
    private int pid;
    private LocalDateTime dfavori;
    
    public Favori(int uid, int pid, LocalDateTime dfavori) {
        this.uid = uid;
        this.pid = pid;
        this.dfavori = dfavori;
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

    public LocalDateTime getDfavori() {
        return dfavori;
    }

    public void setDfavori(LocalDateTime dfavori) {
        this.dfavori = dfavori;
    }
}
