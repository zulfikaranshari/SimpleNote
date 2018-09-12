package com.android.zulfikaranshari.simplenote;

import java.sql.Time;
import java.util.Date;

/**
 * Created by zulfikaranshari on 02/08/2018.
 */

public class NoteModel {
    private String id;
    private String tittle;
    private String note;
    private String dateCreated;
    private String timeCreated;

    public NoteModel(){}

    public NoteModel(String id, String tittle, String note, String dateCreated, String timeCreated) {
        this.id = id;
        this.tittle = tittle;
        this.note = note;
        this.dateCreated = dateCreated;
        this.timeCreated = timeCreated;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(String timeCreated) {
        this.timeCreated = timeCreated;
    }
}
