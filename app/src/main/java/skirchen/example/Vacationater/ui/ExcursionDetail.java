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

import skirchen.example.Vacationater.R;
import skirchen.example.Vacationater.database.Repository;
import skirchen.example.Vacationater.entity.Excursion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ExcursionDetail extends AppCompatActivity {
    final Calendar calendar = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excursion_detail);

        Repository repo = new Repository(getApplication());

        EditText titleEditText = findViewById(R.id.excursion_title);
        TextView dateEditText = findViewById(R.id.excursion_date);
        Button saveButton = findViewById(R.id.save_excursion_button);
        Button deleteButton = findViewById(R.id.delete_excursion_button);
        Button notifyButton = findViewById(R.id.notify_button);

        Boolean intentUpdating = getIntent().getBooleanExtra("updating", false);
        int intentExcursionId = getIntent().getIntExtra("excursionId", 0);
        int intentVacationId = getIntent().getIntExtra("vacationId",0);
        String intentTitle = getIntent().getStringExtra("title");
        String intentDate = getIntent().getStringExtra("date");

        String vacStartDate = getIntent().getStringExtra("startDate");
        String vacEndDate = getIntent().getStringExtra("endDate");

        titleEditText.setText(intentTitle);
        dateEditText.setText(intentDate);

        if(!intentUpdating){
            deleteButton.setVisibility(View.GONE);
            notifyButton.setVisibility(View.GONE);
            dateEditText.setText("Date mm/dd/yy");
            saveButton.setText("CREATE EXCURSION");
        }else{
            deleteButton.setVisibility(View.VISIBLE);
            notifyButton.setVisibility(View.VISIBLE);

            saveButton.setText("UPDATE EXCURSION");
        }

        DatePickerDialog.OnDateSetListener startDateListener = (datePicker, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            dateEditText.setText(sdf.format(calendar.getTime()));
        };

        dateEditText.setOnClickListener((view)->{
            String date = vacStartDate;
            try {
                calendar.setTime(sdf.parse(date));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            new DatePickerDialog(ExcursionDetail.this, startDateListener, calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        saveButton.setOnClickListener((view)->{
            long excursionDate, startDateDate, endDateDate;
            excursionDate = 0;
            startDateDate = 0;
            endDateDate = 0;
            try {
                excursionDate = sdf.parse(dateEditText.getText().toString()).getTime();
                startDateDate = sdf.parse(vacStartDate).getTime();
                endDateDate = sdf.parse(vacEndDate).getTime();

            } catch (ParseException e) {
                Toast.makeText(this, "Please select a date", Toast.LENGTH_SHORT).show();
                return;
            }
            if(startDateDate <= excursionDate && excursionDate <= endDateDate){
                Excursion excursion = new Excursion(intentExcursionId, intentVacationId, titleEditText.getText().toString(), dateEditText.getText().toString());
                if(intentUpdating){
                    repo.updateMany(excursion);
                }else{
                    repo.insertMany(excursion);
                }
                Intent intent = new Intent(this, VacationDetail.class);
                intent.putExtra("vacationId", intentVacationId);
                intent.putExtra("updating", true);
                startActivity(intent);
            }else{
                Toast.makeText(this, "Excursion date is not within vacation dates", Toast.LENGTH_LONG).show();
            }

        });

        deleteButton.setOnClickListener((view)->{
            repo.deleteExcursionById(intentExcursionId);
            Intent intent = new Intent(this, VacationDetail.class);
            intent.putExtra("vacationId", intentVacationId);
            intent.putExtra("updating", true);
            startActivity(intent);
        });

        notifyButton.setOnClickListener((view)->{
            Long trigger;
            try {
                trigger = sdf.parse(dateEditText.getText().toString()).getTime();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            Intent intent = new Intent(ExcursionDetail.this, MyReceiver.class);
            intent.putExtra("title", "WooHoo!!");
            intent.putExtra("text", titleEditText.getText().toString() + " has started today " + dateEditText.getText().toString());
            PendingIntent sender = PendingIntent.getBroadcast(this, ++MainActivity.alertNum, intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender );
        });
    }
}