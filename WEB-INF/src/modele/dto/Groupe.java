package modele.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import utils.BAO;

public class Groupe {
    private int gid;
    private int uid;
    private String pdpGrp;
    private String nomGrp;
    private String description;
    private LocalDateTime dcreat;
    
    public Groupe(int gid, int uid, String pdpGrp, String nomGrp, String description, LocalDateTime dcreat) {
        this.gid = gid;
        this.uid = uid;
        this.pdpGrp = pdpGrp;
        this.nomGrp = nomGrp;
        this.description = description;
        this.dcreat = dcreat;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getPdpGrp() {
        return pdpGrp;
    }

    public void setPdpGrp(String pdpGrp) {
        this.pdpGrp = pdpGrp;
    }

    public String getNomGrp() {
        return BAO.escapeHTML(nomGrp);
    }

    public String getHTMLNomGrp() {
        return nomGrp;
    }

    public void setNomGrp(String nomGrp) {
        this.nomGrp = nomGrp;
    }

    public String getDescription() {
        return BAO.escapeHTML(description);
    }

    public String getHTMLDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDcreat() {
        return dcreat;
    }

    public String getDcreatAsDate() {
        return dcreat.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public void setDcreat(LocalDateTime dcreat) {
        this.dcreat = dcreat;
    }
}
