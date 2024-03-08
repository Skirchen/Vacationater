package skirchen.example.Vacationater.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "vacation")
public class Vacation {
    @PrimaryKey(autoGenerate = true)
    private int vacationId;
    private String title;
    private String lodging;
    private String startDate;
    private String endDate;

    public Vacation(int vacationId, String title, String lodging, String startDate, String endDate) {
        this.vacationId = vacationId;
        this.title = title;
        this.lodging = lodging;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public String getLodging() {
        return lodging;
    }

    public void setLodging(String lodging) {
        this.lodging = lodging;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Vacation{" +
                "vacationId=" + vacationId +
                ", title='" + title + '\'' +
                ", lodging='" + lodging + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}

