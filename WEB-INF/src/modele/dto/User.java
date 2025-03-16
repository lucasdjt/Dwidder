package modele.dto;

import java.time.*;
import utils.BAO;

public class User {
    private int uid;
    private String idPseudo;
    private String pseudo;
    private String prenom;
    private String nomUser;
    private String email;
    private String mdp;
    private String bio;
    private String pdp;
    private LocalDateTime dinsc;
    private LocalDate dnaiss;
    private String loca;
    private boolean admin;

    public User(int uid, String idPseudo, String pseudo, String prenom, String nomUser, String email, String mdp,
            String bio, String pdp, LocalDateTime dinsc, LocalDate dnaiss, String loca, boolean admin) {
        this.uid = uid;
        this.idPseudo = idPseudo;
        this.pseudo = pseudo;
        this.prenom = prenom;
        this.nomUser = nomUser;
        this.email = email;
        this.mdp = mdp;
        this.bio = bio;
        this.pdp = pdp;
        this.dinsc = dinsc;
        this.dnaiss = dnaiss;
        this.loca = loca;
        this.admin = admin;
    }

    public User(){
        this.idPseudo = null;
        this.pseudo = null;
        this.prenom = null;
        this.nomUser = null;
        this.email = null;
        this.mdp = null;
        this.bio = null;
        this.pdp = null;
        this.dinsc = LocalDateTime.now();
        this.dnaiss = null;
        this.loca = null;
        this.admin = false;
    }

    @Override
    public String toString() {
        return "User [uid=" + uid + ", idPseudo=" + idPseudo + ", pseudo=" + pseudo + ", prenom=" + prenom
                + ", nomUser=" + nomUser + ", email=" + email + ", mdp=" + mdp + ", bio=" + bio + ", pdp=" + pdp
                + ", dinsc=" + dinsc + ", dnaiss=" + dnaiss + ", loca=" + loca + ", admin=" + admin + "]";
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getIdPseudo() {
        return idPseudo;
    }

    public void setIdPseudo(String idPseudo) {
        this.idPseudo = idPseudo;
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

    public String getPrenom() {
        return BAO.escapeHTML(prenom);
    }

    public String getHTMLPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNomUser() {
        return BAO.escapeHTML(nomUser);
    }
    
    public String getHTMLNomUser() {
        return nomUser;
    }

    public void setNomUser(String nomUser) {
        this.nomUser = nomUser;
    }

    public String getEmail() {
        return BAO.escapeHTML(email);
    }

    public String getHTMLEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMdp() {
        return BAO.escapeHTML(mdp);
    }

    public String getHTMLMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getBio() {
        return BAO.escapeHTML(bio);
    }

    public String getHTMLBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPdp() {
        return pdp;
    }

    public void setPdp(String pdp) {
        this.pdp = pdp;
    }

    public LocalDateTime getDinsc() {
        return dinsc;
    }

    public String getDinscAsDate() {
        return dinsc.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public void setDinsc(LocalDateTime dinsc) {
        this.dinsc = dinsc;
    }

    public LocalDate getDnaiss() {
        return dnaiss;
    }

    public void setDnaiss(LocalDate dnaiss) {
        this.dnaiss = dnaiss;
    }

    public String getLoca() {
        return BAO.escapeHTML(loca);
    }

    public String getHTMLLoca() {
        return loca;
    }

    public void setLoca(String loca) {
        this.loca = loca;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}