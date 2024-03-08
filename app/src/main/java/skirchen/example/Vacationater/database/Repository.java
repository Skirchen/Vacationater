package skirchen.example.Vacationater.database;

import android.app.Application;

import skirchen.example.Vacationater.dao.ExcursionDao;
import skirchen.example.Vacationater.dao.VacationDao;
import skirchen.example.Vacationater.entity.Excursion;
import skirchen.example.Vacationater.entity.Vacation;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private VacationDao mVacationDao;
    private ExcursionDao mExcursionDao;
    private Vacation vacation;
    private Excursion excursion;
    private List<Vacation> mVacations;
    private List<Excursion> mExcursions;

    private static int NUMBER_OF_THREADS = 4;

    public static final ExecutorService dbExec = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application){
        AppDatabaseBuilder db = AppDatabaseBuilder.getDatabase(application);
        mVacationDao = db.vacationDao();
        mExcursionDao = db.excursionDao();
    }

    public void insertMany(Vacation... vacations){
        dbExec.execute(() ->{
            mVacationDao.insertVacations(vacations);
        });
    }

    public void insertMany(Excursion... excursions){
        dbExec.execute(() ->{
            mExcursionDao.insert(excursions);
        });
    }

    public void updateMany(Vacation... vacations){
        dbExec.execute(()->{
            mVacationDao.updateVacations(vacations);
        });
    }

    public void updateMany(Excursion... excursions){
        dbExec.execute(()->{
            mExcursionDao.update(excursions);
        });
    }
    public void delete(Vacation vacation){
        dbExec.execute(() ->{
            mVacationDao.deleteVacation(vacation);
        });
    }

    public void deleteVacationById(int id){
        dbExec.execute(()->{
            Vacation tempVacation = findVacation(id);
            mVacationDao.deleteVacation(tempVacation);
        });
    }

    public void deleteExcursionById(int id){
        dbExec.execute(()->{
            Excursion tempExcursion = findExcursion(id);
            mExcursionDao.delete(tempExcursion);
        });
    }
    public Vacation findVacation(int id){

         dbExec.execute(()->{
           vacation = mVacationDao.getVacation(id);
         });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return vacation;
    }

    public Excursion findExcursion(int excursionId){

        dbExec.execute(()->{
            excursion = mExcursionDao.find(excursionId);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return excursion;
    }


    public List<Vacation> getAllVacations(){
        dbExec.execute(() -> {
            mVacations = mVacationDao.getAll();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mVacations;
    }

    public List<Excursion> getAllExcursions(int id){
        dbExec.execute(() -> {
            mExcursions = mExcursionDao.getAssociatedExcursions(id);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mExcursions;
    }

}
