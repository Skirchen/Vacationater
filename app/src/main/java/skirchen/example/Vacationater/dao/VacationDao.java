package skirchen.example.Vacationater.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import skirchen.example.Vacationater.entity.Vacation;

import java.util.List;

@Dao
public interface VacationDao {
    @Insert
    public void insertVacations(Vacation... vacations);
    @Update
    public void updateVacations(Vacation... vacations);
    @Delete
    public void deleteVacation(Vacation vacation);
    @Query("SELECT * FROM vacation WHERE vacationId = :vacationId")
    Vacation getVacation(int vacationId);
    @Query("SELECT * FROM vacation")
    List<Vacation> getAll();

}
