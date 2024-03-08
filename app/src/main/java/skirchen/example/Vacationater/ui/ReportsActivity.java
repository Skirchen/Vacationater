package skirchen.example.Vacationater.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import skirchen.example.Vacationater.R;
import skirchen.example.Vacationater.database.Repository;
import skirchen.example.Vacationater.entity.Vacation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ReportsActivity extends AppCompatActivity {
    private static boolean weekly = false;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yy", Locale.US);
    private LocalDate monthMin = LocalDate.now();
    private LocalDate monthMax = weekly ? monthMin.plusDays(7) : monthMin.plusMonths(1);
    private Repository repository = new Repository(getApplication());
    List<Vacation> mVacations = repository.getAllVacations();
    List<Vacation> filteredVacations = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        Button monthButton = findViewById(R.id.report_month);
        Button weekButton = findViewById(R.id.report_week);
        TextView titleTextView = findViewById(R.id.report_title_textView);

        titleTextView.setText(weekly ? "Vacations in the next 7 days" : "Vacations in the next 30 days");

        for(Vacation vacation: mVacations){
            LocalDate vacDate = LocalDate.parse(vacation.getStartDate(), dtf);
            if((monthMin.isBefore(vacDate) || monthMin.isEqual(vacDate)) && (vacDate.isBefore(monthMax) || vacDate.isEqual(monthMax)) ){
                filteredVacations.add(vacation);
            }
        }
        ReportAdapter reportAdapter = new ReportAdapter(filteredVacations,getApplication());
        RecyclerView reportRV = findViewById(R.id.reportRV);
        reportRV.setAdapter(reportAdapter);
        reportRV.setLayoutManager(new LinearLayoutManager(this));


        monthButton.setOnClickListener((view) -> {
            ReportsActivity.weekly = false;
            recreate();
        });
        weekButton.setOnClickListener((view) -> {
            ReportsActivity.weekly = true;
            recreate();
        });
    }
}

