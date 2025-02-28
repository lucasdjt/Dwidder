package modele.dto;

import java.time.LocalDateTime;

public class Groupe {
    private int gid;
    private int uidAdmin;
    private String nomGrp;
    private String description;
    private LocalDateTime dateCreation;
    
    public Groupe(int gid, int uidAdmin, String nomGrp, String description, LocalDateTime dateCreation) {
        this.gid = gid;
        this.uidAdmin = uidAdmin;
        this.nomGrp = nomGrp;
        this.description = description;
        this.dateCreation = dateCreation;
    }

    @Override
    public String toString() {
        return "Groupe [gid=" + gid + ", uidAdmin=" + uidAdmin + ", nomGrp=" + nomGrp + ", description=" + description
                + ", dateCreation=" + dateCreation + "]";
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public int getUidAdmin() {
        return uidAdmin;
    }

    public void setUidAdmin(int uidAdmin) {
        this.uidAdmin = uidAdmin;
    }

    public String getNomGrp() {
        return nomGrp;
    }

    public void setNomGrp(String nomGrp) {
        this.nomGrp = nomGrp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }
}
