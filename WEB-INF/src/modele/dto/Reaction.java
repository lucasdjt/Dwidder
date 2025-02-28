package modele.dto;

import java.time.LocalDateTime;

public class Reaction {
    private int uid;
    private int pid;
    private String type;
    private LocalDateTime dateReact;
    
    public Reaction(int uid, int pid, String type, LocalDateTime dateReact) {
        this.uid = uid;
        this.pid = pid;
        this.type = type;
        this.dateReact = dateReact;
    }

    @Override
    public String toString() {
        return "Reaction [uid=" + uid + ", pid=" + pid + ", type=" + type + ", dateReact=" + dateReact + "]";
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getDateReact() {
        return dateReact;
    }

    public void setDateReact(LocalDateTime dateReact) {
        this.dateReact = dateReact;
    }
}

