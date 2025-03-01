package modele.dto;

public class Conversation {
    private int uidEnvoyeur;
    private int uidReceveur;

    public Conversation(int uidEnvoyeur, int uidReceveur) {
        this.uidEnvoyeur = uidEnvoyeur;
        this.uidReceveur = uidReceveur;
    }

    @Override
    public String toString() {
        return "Conversation [uidEnvoyeur=" + uidEnvoyeur + ", uidReceveur=" + uidReceveur + "]";
    }

    public int getUidEnvoyeur() {
        return uidEnvoyeur;
    }

    public void setUidEnvoyeur(int uidEnvoyeur) {
        this.uidEnvoyeur = uidEnvoyeur;
    }

    public int getUidReceveur() {
        return uidReceveur;
    }

    public void setUidReceveur(int uidReceveur) {
        this.uidReceveur = uidReceveur;
    }
}
