package modele.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import utils.BAO;

public class PostDetails {
    private int pid;
    private Integer gid;
    private String nomGrp;
    private Integer pidParent;
    private String contenu;
    private String media;
    private LocalDateTime dpub;
    private LocalDateTime dfin;
    private String pdp;
    private String pseudo;
    private int uid;
    private int uidAdmin;
    private int nbLikes;
    private int nbComm;
    private String idPseudo;
    
    public PostDetails(int pid, Integer gid, String nomGrp, Integer pidParent, String contenu, String media,
            LocalDateTime dpub, LocalDateTime dfin, String pdp, String pseudo, int uid, int uidAdmin, int nbLikes,
            int nbComm, String idPseudo) {
        this.pid = pid;
        this.gid = gid;
        this.nomGrp = nomGrp;
        this.pidParent = pidParent;
        this.contenu = contenu;
        this.media = media;
        this.dpub = dpub;
        this.dfin = dfin;
        this.pdp = pdp;
        this.pseudo = pseudo;
        this.uid = uid;
        this.uidAdmin = uidAdmin;
        this.nbLikes = nbLikes;
        this.nbComm = nbComm;
        this.idPseudo = idPseudo;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
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

    public Integer getPidParent() {
        return pidParent;
    }

    public void setPidParent(Integer pidParent) {
        this.pidParent = pidParent;
    }

    public String getContenu() {
        return BAO.escapeHTML(contenu);
    }

    public String getHTMLContenu() {
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

    public String getDpubAsDate() {
        return dpub.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public void setDpub(LocalDateTime dpub) {
        this.dpub = dpub;
    }

    public LocalDateTime getDfin() {
        return dfin;
    }

    public void setDfin(LocalDateTime dfin) {
        this.dfin = dfin;
    }

    public String getPdp() {
        return pdp;
    }

    public void setPdp(String pdp) {
        this.pdp = pdp;
    }

    public String getPseudo() {
        return BAO.escapeHTML(pseudo);
    }

    public String getHTMLPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getUidAdmin() {
        return uidAdmin;
    }

    public void setUidAdmin(int uidAdmin) {
        this.uidAdmin = uidAdmin;
    }
    
    public int getNbLikes() {
        return nbLikes;
    }

    public void setNbLikes(int nbLikes) {
        this.nbLikes = nbLikes;
    }

    public int getNbComm() {
        return nbComm;
    }

    public void setNbComm(int nbComm) {
        this.nbComm = nbComm;
    }

    public String getIdPseudo() {
        return idPseudo;
    }

    public void setIdPseudo(String idPseudo) {
        this.idPseudo = idPseudo;
    }

    public long getDuree(){
        return (dfin != null) ? java.time.Duration.between(dpub, dfin).toHours() : 1000;
    }
}
