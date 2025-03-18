package modele.dto;

import java.time.LocalDateTime;

public class Logs {
    private int lid;
    private LocalDateTime daction;
    private String pseudoLog;
    private String textLog;

    public Logs(int lid, LocalDateTime daction, String pseudoLog, String textLog) {
        this.lid = lid;
        this.daction = daction;
        this.pseudoLog = pseudoLog;
        this.textLog = textLog;
    }

    public int getLid() {
        return lid;
    }
    public void setLid(int lid) {
        this.lid = lid;
    }
    public LocalDateTime getDaction() {
        return daction;
    }
    public void setDaction(LocalDateTime daction) {
        this.daction = daction;
    }
    public String getPseudoLog() {
        return pseudoLog;
    }
    public void setPseudoLog(String pseudoLog) {
        this.pseudoLog = pseudoLog;
    }
    public String getTextLog() {
        return textLog;
    }
    public void setTextLog(String textLog) {
        this.textLog = textLog;
    }
    
    
}
