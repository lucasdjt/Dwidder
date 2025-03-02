package modele.dto;

public class Conversation {
    private int cid;
    private int uidEnvoyeur;
    private int uidReceveur;

    public Conversation(int cid, int uidEnvoyeur, int uidReceveur) {
        this.cid = cid;
        this.uidEnvoyeur = uidEnvoyeur;
        this.uidReceveur = uidReceveur;
    }

    @Override
    public String toString() {
        return "Conversation [cid=" + cid + ", uidEnvoyeur=" + uidEnvoyeur + ", uidReceveur=" + uidReceveur + "]";
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
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
