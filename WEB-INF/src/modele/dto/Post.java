package modele.dto;

import java.time.Duration;
import java.time.LocalDateTime;

public class Post {
    private int pid;
    private int uid;
    private Integer gid;
    private Integer pidParent;
    private String contenu;
    private String media;
    private LocalDateTime dpub;
    private Duration dureePost;

    public Post(int pid, int uid, Integer gid, Integer pidParent, String contenu, String media, LocalDateTime dpub,
            Duration dureePost) {
        this.pid = pid;
        this.uid = uid;
        this.gid = gid;
        this.pidParent = pidParent;
        this.contenu = contenu;
        this.media = media;
        this.dpub = dpub;
        this.dureePost = dureePost;
    }

    public Post() {
        gid = null;
        pidParent = null;
        contenu = null;
        media = null;
        dpub = null;
        dureePost = null;
    }

    @Override
    public String toString() {
        return "Post [pid=" + pid + ", uid=" + uid + ", gid=" + gid + ", pidParent=" + pidParent + ", contenu="
                + contenu + ", media=" + media + ", dpub=" + dpub + ", dureePost=" + dureePost + "]";
    }
    
    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public Integer getPidParent() {
        return pidParent;
    }

    public void setPidParent(Integer pidParent) {
        this.pidParent = pidParent;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public LocalDateTime getDpub() {
        return dpub;
    }

    public void setDpub(LocalDateTime dpub) {
        this.dpub = dpub;
    }

    public Duration getDureePost() {
        return dureePost;
    }

    public void setDureePost(Duration dureePost) {
        this.dureePost = dureePost;
    }
}
