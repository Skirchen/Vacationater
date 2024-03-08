package skirchen.example.Vacationater.ui;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.SearchView;

import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import skirchen.example.Vacationater.R;
import skirchen.example.Vacationater.database.Repository;
import skirchen.example.Vacationater.entity.Vacation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public static int alertNum;
    LocalDate date = LocalDate.now();
    LocalDate monthMin = YearMonth.now().atDay(1);
    LocalDate monthMax = YearMonth.now().atEndOfMonth();
    DateTimeFormatter dtf  = DateTimeFormatter.ofPattern("MM/dd/yy", Locale.US);

    List<Vacation> vacations;
    private Repository repo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SearchView searchView = (SearchView) findViewById(R.id.sv);
        searchView.clearFocus();
        repo = new Repository(getApplication());
        RecyclerView rvVacations = (RecyclerView) findViewById(R.id.MainRecyclerView);
        vacations =  repo.getAllVacations();
        VacationAdapter adapter = new VacationAdapter(vacations, getApplication());
        rvVacations.setAdapter(adapter);
        rvVacations.setLayoutManager(new LinearLayoutManager(this));
        if(vacations.isEmpty()){
            Vacation vacation1 = new Vacation(0,"Palisade Head", "test1", "08/24/24", "08/26/24");
            Vacation vacation2 = new Vacation(0,"Shovel Point", "test1", "08/24/24", "08/26/24");
            Vacation vacation3 = new Vacation(0,"Temperance", "test1", "08/24/24", "08/26/24");
            repo.insertMany(vacation1,vacation2,vacation3);
            recreate();
        }
        Button reportButton = findViewById(R.id.report_button);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<Vacation> filteredVacations = new ArrayList<>();
                for (Vacation vacation : vacations){
                    if(vacation.getTitle().toLowerCase().startsWith(s.toLowerCase())){
                        filteredVacations.add(vacation);
                    }
                }
                if(filteredVacations.isEmpty()){
                    Toast.makeText(MainActivity.this, "No Matches", Toast.LENGTH_SHORT).show();
                }else{
                    adapter.filterList(filteredVacations);
                    adapter.notifyDataSetChanged();
                }
                return true;
            }
        });

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener((view)->{
            Intent intent = new Intent(this, VacationDetail.class);
            startActivity(intent);
        });

        reportButton.setOnClickListener((view)->{
            Intent intent = new Intent(this, ReportsActivity.class);
            startActivity(intent);
        });
        LocalDate tempDate = LocalDate.parse("10/13/23", dtf);
        System.out.println(date);
        System.out.println(monthMin);
        System.out.println(monthMax);




        if(tempDate.isAfter(monthMin) && tempDate.isBefore(monthMax)){
            System.out.println("True");
        }else{
            System.out.println("False");
        }


    }

}