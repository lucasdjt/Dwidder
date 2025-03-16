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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + uid;
        result = prime * result + ((idPseudo == null) ? 0 : idPseudo.hashCode());
        result = prime * result + ((pseudo == null) ? 0 : pseudo.hashCode());
        result = prime * result + ((prenom == null) ? 0 : prenom.hashCode());
        result = prime * result + ((nomUser == null) ? 0 : nomUser.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((mdp == null) ? 0 : mdp.hashCode());
        result = prime * result + ((bio == null) ? 0 : bio.hashCode());
        result = prime * result + ((pdp == null) ? 0 : pdp.hashCode());
        result = prime * result + ((dinsc == null) ? 0 : dinsc.hashCode());
        result = prime * result + ((dnaiss == null) ? 0 : dnaiss.hashCode());
        result = prime * result + ((loca == null) ? 0 : loca.hashCode());
        result = prime * result + (admin ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (uid != other.uid)
            return false;
        if (idPseudo == null) {
            if (other.idPseudo != null)
                return false;
        } else if (!idPseudo.equals(other.idPseudo))
            return false;
        if (pseudo == null) {
            if (other.pseudo != null)
                return false;
        } else if (!pseudo.equals(other.pseudo))
            return false;
        if (prenom == null) {
            if (other.prenom != null)
                return false;
        } else if (!prenom.equals(other.prenom))
            return false;
        if (nomUser == null) {
            if (other.nomUser != null)
                return false;
        } else if (!nomUser.equals(other.nomUser))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (mdp == null) {
            if (other.mdp != null)
                return false;
        } else if (!mdp.equals(other.mdp))
            return false;
        if (bio == null) {
            if (other.bio != null)
                return false;
        } else if (!bio.equals(other.bio))
            return false;
        if (pdp == null) {
            if (other.pdp != null)
                return false;
        } else if (!pdp.equals(other.pdp))
            return false;
        if (dinsc == null) {
            if (other.dinsc != null)
                return false;
        } else if (!dinsc.equals(other.dinsc))
            return false;
        if (dnaiss == null) {
            if (other.dnaiss != null)
                return false;
        } else if (!dnaiss.equals(other.dnaiss))
            return false;
        if (loca == null) {
            if (other.loca != null)
                return false;
        } else if (!loca.equals(other.loca))
            return false;
        if (admin != other.admin)
            return false;
        return true;
    }
}