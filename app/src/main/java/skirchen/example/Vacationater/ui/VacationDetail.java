package skirchen.example.Vacationater.ui;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import skirchen.example.Vacationater.R;
import skirchen.example.Vacationater.database.Repository;
import skirchen.example.Vacationater.entity.Excursion;
import skirchen.example.Vacationater.entity.Vacation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class VacationDetail extends AppCompatActivity {
    final Calendar calendarStart = Calendar.getInstance();
    private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_detail);

        Repository repo = new Repository(getApplication());
        int intentId = getIntent().getIntExtra("vacationId", 0);
        Vacation vacation = repo.findVacation(intentId);
        List<Excursion> mExcursions = repo.getAllExcursions(intentId);

        Button saveButton = findViewById(R.id.save);
        Button deleteButton = findViewById(R.id.delete);
        Button addExcursionButton = findViewById(R.id.add_excursion_button);
        Button shareButton = findViewById(R.id.share);
        Button notifyButton = findViewById(R.id.notify);

        EditText title = findViewById(R.id.title);
        EditText lodging = findViewById(R.id.lodging);
        TextView startDate = findViewById(R.id.start_date);
        TextView endDate = findViewById(R.id.end_date);

        Boolean updating = getIntent().getBooleanExtra("updating", false);

        saveButton.setText(updating ? "UPDATE VACATION" : "CREATE VACATION");
        deleteButton.setVisibility(updating ? View.VISIBLE : View.GONE);
        addExcursionButton.setVisibility(updating ? View.VISIBLE : View.GONE);
        shareButton.setVisibility(updating ? View.VISIBLE : View.GONE);
        notifyButton.setVisibility(updating ? View.VISIBLE : View.GONE);

        DatePickerDialog.OnDateSetListener startDateListener = (datePicker, year, month, dayOfMonth) -> {
            calendarStart.set(Calendar.YEAR, year);
            calendarStart.set(Calendar.MONTH, month);
            calendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            startDate.setText(sdf.format(calendarStart.getTime()));
        };

        DatePickerDialog.OnDateSetListener endDateListener = (datePicker, year, month, dayOfMonth) -> {
            calendarStart.set(Calendar.YEAR, year);
            calendarStart.set(Calendar.MONTH, month);
            calendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            endDate.setText(sdf.format(calendarStart.getTime()));
        };


        if(intentId != 0) {
            title.setText(vacation.getTitle());
            lodging.setText(vacation.getLodging());
            startDate.setText(vacation.getStartDate());
            endDate.setText(vacation.getEndDate());
        }

        RecyclerView rvExcursions = (RecyclerView) findViewById(R.id.recyclerView);
        ExcursionAdapter adapter;
        if(intentId != 0){
            adapter = new ExcursionAdapter(mExcursions, vacation.getStartDate(), vacation.getEndDate());
        }else{
            adapter = new ExcursionAdapter(mExcursions);
        }
        rvExcursions.setAdapter(adapter);
        rvExcursions.setLayoutManager(new LinearLayoutManager(this));

        startDate.setOnClickListener((view)->{
            String date = "08/01/23";

            try {
                calendarStart.setTime(sdf.parse(date));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            new DatePickerDialog(VacationDetail.this, startDateListener, calendarStart.get(Calendar.YEAR),
                    calendarStart.get(Calendar.MONTH), calendarStart.get(Calendar.DAY_OF_MONTH)).show();
        });

       endDate.setOnClickListener((view)->{
            String date = "08/01/23";
            try {
                calendarStart.setTime(sdf.parse(date));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            new DatePickerDialog(VacationDetail.this, endDateListener, calendarStart.get(Calendar.YEAR),
                    calendarStart.get(Calendar.MONTH), calendarStart.get(Calendar.DAY_OF_MONTH)).show();
        });

        saveButton.setOnClickListener((view) -> {
            Vacation tempVacation = new Vacation(intentId, title.getText().toString(), lodging.getText().toString(), startDate.getText().toString(), endDate.getText().toString());
            long startDateDate = 0;
            long endDateDate = 0;
            try {
                startDateDate = sdf.parse(startDate.getText().toString()).getTime();
                endDateDate = sdf.parse(endDate.getText().toString()).getTime();
            } catch (ParseException e) {
                Toast.makeText(this, "Please select a date", Toast.LENGTH_SHORT).show();
                return;
            }
            if (endDateDate <= startDateDate) {
                Toast.makeText(this, "End date must be after start date.",Toast.LENGTH_LONG).show();
            }else{
                if (updating) {
                    repo.updateMany(tempVacation);
                } else {
                    repo.insertMany(tempVacation);
                }
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        });
        addExcursionButton.setOnClickListener((view)-> {
            Vacation tempVacation = new Vacation(intentId, title.getText().toString(), lodging.getText().toString(), startDate.getText().toString(), endDate.getText().toString());
            if (updating) {
                repo.updateMany(tempVacation);
            } else {
                repo.insertMany(tempVacation);
            }
            Intent intent = new Intent(this, ExcursionDetail.class);
            intent.putExtra("vacationId", intentId);
            intent.putExtra("startDate", startDate.getText().toString());
            intent.putExtra("endDate", endDate.getText().toString());
            startActivity(intent);
        });
        deleteButton.setOnClickListener((view)->{
            if(mExcursions.size() < 1){
                repo.deleteVacationById(intentId);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(this, "Cannot delete vacations with excursions attached", Toast.LENGTH_LONG).show();
            }
        });

        shareButton.setOnClickListener((view)->{
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TITLE, vacation.getTitle());
            sendIntent.putExtra(Intent.EXTRA_TEXT, vacation.getTitle() + "\n"
                                                        + vacation.getLodging() + "\n"
                                                        + vacation.getStartDate() + "\n"
                                                        + vacation.getEndDate());
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent,null);
            startActivity(shareIntent);
        });

        notifyButton.setOnClickListener((view) -> {
            Long startTrigger, endTrigger;
            try {
                startTrigger = sdf.parse(startDate.getText().toString()).getTime();
                endTrigger = sdf.parse(endDate.getText().toString()).getTime();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            Intent intent = new Intent(VacationDetail.this, MyReceiver.class);
            intent.putExtra("title", "WooHoo!!");
            intent.putExtra("text", vacation.getTitle() + " has started today " + vacation.getStartDate());
            PendingIntent sender = PendingIntent.getBroadcast(this, ++MainActivity.alertNum, intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, startTrigger, sender );

            intent = new Intent(VacationDetail.this, MyReceiver.class);
            intent.putExtra("title", "Sad Day :(");
            intent.putExtra("text", vacation.getTitle() + " is ending today " + vacation.getEndDate());
            sender = PendingIntent.getBroadcast(this, ++MainActivity.alertNum,intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.set(AlarmManager.RTC_WAKEUP, endTrigger, sender);

        });
    }
}