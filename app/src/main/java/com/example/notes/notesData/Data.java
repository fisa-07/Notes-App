package com.example.notes.notesData;

public class Data {
    private String note;
    private String day;

    public void setNote(String note) {
        this.note = note;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Data(String note, String day) {
        this.note = note;
        this.day = day;
    }

    public String getNote() {
        return note;
    }

    public String getDay() {
        return day;
    }
}
