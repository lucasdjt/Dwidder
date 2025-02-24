package modele.dto;

import java.sql.Date;

public class User {
    private int uid;
    private String id_pseudo;
    private String pseudo;
    private String prenom;
    private String nom_user;
    private String email;
    private String mdp;
    private String bio;
    private String pdp;
    private Date date_insc;
    private Date date_naiss;
    private String loca;
    private String sexe;
    private String num_tel;
    private String langue;
    private String admin;

    public User(int uid, String id_pseudo, String pseudo, String prenom, String nom_user, String email, String mdp, String bio, String pdp, Date date_insc, Date date_naiss, String loca, String sexe, String num_tel, String langue, String admin) {
        this.uid = uid;
        this.id_pseudo = id_pseudo;
        this.pseudo = pseudo;
        this.prenom = prenom;
        this.nom_user = nom_user;
        this.email = email;
        this.mdp = mdp;
        this.bio = bio;
        this.pdp = pdp;
        this.date_insc = date_insc;
        this.date_naiss = date_naiss;
        this.loca = loca;
        this.sexe = sexe;
        this.num_tel = num_tel;
        this.langue = langue;
        this.admin = admin;
    }

    public User() {}

    // Getters and Setters
    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getId_pseudo() {
        return id_pseudo;
    }

    public void setId_pseudo(String id_pseudo) {
        this.id_pseudo = id_pseudo;
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

    public String getNom_user() {
        return nom_user;
    }

    public void setNom_user(String nom_user) {
        this.nom_user = nom_user;
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

    public Date getDate_insc() {
        return date_insc;
    }

    public void setDate_insc(Date date_insc) {
        this.date_insc = date_insc;
    }

    public Date getDate_naiss() {
        return date_naiss;
    }

    public void setDate_naiss(Date date_naiss) {
        this.date_naiss = date_naiss;
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

    public String getNum_tel() {
        return num_tel;
    }

    public void setNum_tel(String num_tel) {
        this.num_tel = num_tel;
    }

    public String getLangue() {
        return langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }
}