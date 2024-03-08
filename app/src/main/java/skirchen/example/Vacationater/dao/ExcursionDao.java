package skirchen.example.Vacationater.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import skirchen.example.Vacationater.entity.Excursion;

import java.util.List;

@Dao
public interface ExcursionDao {
    @Insert
    public void insert(Excursion... excursions);
    @Update
    public void update(Excursion... excursions);
    @Delete
    public void delete(Excursion... excursions);
    @Query("SELECT * FROM excursion WHERE vacationId = :vacationId")
    List<Excursion> getAssociatedExcursions(int vacationId);
    @Query("SELECT * FROM excursion WHERE excursionId = :excursionId")
    Excursion find(int excursionId);
    @Query("SELECT * FROM excursion")
    List<Excursion> getAll();

}
