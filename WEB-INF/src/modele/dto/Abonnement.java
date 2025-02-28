package modele.dto;

import java.time.LocalDateTime;

public class Abonnement {
    private int uidAbonne;
    private int uidAbonnement;
    private LocalDateTime dateAbonnement;
    
    public Abonnement(int uidAbonne, int uidAbonnement, LocalDateTime dateAbonnement) {
        this.uidAbonne = uidAbonne;
        this.uidAbonnement = uidAbonnement;
        this.dateAbonnement = dateAbonnement;
    }

    @Override
    public String toString() {
        return "Abonnement [uidAbonne=" + uidAbonne + ", uidAbonnement=" + uidAbonnement + ", dateAbonnement="
                + dateAbonnement + "]";
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

    public LocalDateTime getDateAbonnement() {
        return dateAbonnement;
    }

    public void setDateAbonnement(LocalDateTime dateAbonnement) {
        this.dateAbonnement = dateAbonnement;
    }
}
