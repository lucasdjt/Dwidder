package modele.dto;

public class Reaction {
    private int uid;
    private int pid;
    private String type;
    
    public Reaction(int uid, int pid, String type) {
        this.uid = uid;
        this.pid = pid;
        this.type = type;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getType() {
        return type;
    }

    public String getTypeEmoji(){
        switch (type) {
            case "LIKES":
                return "👍";
            case "LOVES":
                return "❤️";
            case "FIRES":
                return "🔥";
            case "JOYYY":
                return "😂";
            case "SADDD":
                return "😢";
            case "ANGER":
                return "😡";
            case "THIFT":
                return "🤔";
            default:
                return "❓";
        }
    }

    public void setType(String type) {
        this.type = type;
    }
}

