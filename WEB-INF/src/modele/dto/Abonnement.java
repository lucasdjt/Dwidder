package modele.dto;

import java.time.LocalDateTime;

public class Abonnement {
    private int uidAbonne;
    private int uidAbonnement;
    private LocalDateTime dabonnement;
    
    public Abonnement(int uidAbonne, int uidAbonnement, LocalDateTime dabonnement) {
        this.uidAbonne = uidAbonne;
        this.uidAbonnement = uidAbonnement;
        this.dabonnement = dabonnement;
    }

    public Abonnement() {
        dabonnement = null;
    }

    @Override
    public String toString() {
        return "Abonnement [uidAbonne=" + uidAbonne + ", uidAbonnement=" + uidAbonnement + ", dabonnement="
                + dabonnement + "]";
    }

    public int getUidAbonne() {
        return uidAbonne;
    }

    public void setUidAbonne(int uidAbonne) {
        this.uidAbonne = uidAbonne;
    }

    public int getUidAbonnement() {
        return uidAbonnement;
    }

    public void setUidAbonnement(int uidAbonnement) {
        this.uidAbonnement = uidAbonnement;
    }

    public LocalDateTime getDabonnement() {
        return dabonnement;
    }

    public void setDabonnement(LocalDateTime dabonnement) {
        this.dabonnement = dabonnement;
    }
}
