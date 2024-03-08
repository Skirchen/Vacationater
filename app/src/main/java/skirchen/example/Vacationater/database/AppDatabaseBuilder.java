package skirchen.example.Vacationater.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import skirchen.example.Vacationater.dao.ExcursionDao;
import skirchen.example.Vacationater.dao.VacationDao;
import skirchen.example.Vacationater.entity.Excursion;
import skirchen.example.Vacationater.entity.Vacation;

@Database(entities = {Vacation.class, Excursion.class}, version = 1)
public abstract class AppDatabaseBuilder extends RoomDatabase {
    public abstract VacationDao vacationDao();
    public abstract ExcursionDao excursionDao();

    private static volatile AppDatabaseBuilder INSTANCE;

    public static AppDatabaseBuilder getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (AppDatabaseBuilder.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabaseBuilder.class, "VacationApp.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
