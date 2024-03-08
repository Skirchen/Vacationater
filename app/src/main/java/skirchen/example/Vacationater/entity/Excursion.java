package skirchen.example.Vacationater.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "excursion")
public class Excursion {
    @PrimaryKey(autoGenerate = true)
    int excursionId;
    int vacationId;
    String title;
    String date;

    public Excursion(int excursionId, int vacationId, String title, String date) {
        this.excursionId = excursionId;
        this.vacationId = vacationId;
        this.title = title;
        this.date = date;
    }

    public int getExcursionId() {
        return excursionId;
    }

    public void setExcursionId(int excursionId) {
        this.excursionId = excursionId;
    }

    public int getVacationId() {
        return vacationId;
    }

    public void setVacationId(int vacationId) {
        this.vacationId = vacationId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
