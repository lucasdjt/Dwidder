package modele.dto;

import java.time.*;

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
    private String sexe;
    private String tel;
    private String langue;
    private boolean admin;

    public User(int uid, String idPseudo, String pseudo, String prenom, String nomUser, String email, String mdp,
            String bio, String pdp, LocalDateTime dinsc, LocalDate dnaiss, String loca, String sexe,
            String tel, String langue, boolean admin) {
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
        this.sexe = sexe;
        this.tel = tel;
        this.langue = langue;
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
        this.sexe = null;
        this.tel = null;
        this.langue = null;
        this.admin = false;
    }

    @Override
    public String toString() {
        return "User [uid=" + uid + ", idPseudo=" + idPseudo + ", pseudo=" + pseudo + ", prenom=" + prenom
                + ", nomUser=" + nomUser + ", email=" + email + ", mdp=" + mdp + ", bio=" + bio + ", pdp=" + pdp
                + ", dinsc=" + dinsc + ", dnaiss=" + dnaiss + ", loca=" + loca + ", sexe=" + sexe
                + ", tel=" + tel + ", langue=" + langue + ", admin=" + admin + "]";
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
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNomUser() {
        return nomUser;
    }

    public void setNomUser(String nomUser) {
        this.nomUser = nomUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getBio() {
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
        return loca;
    }

    public void setLoca(String loca) {
        this.loca = loca;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getLangue() {
        return langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}