package skirchen.example.Vacationater.ui;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import skirchen.example.Vacationater.R;
import skirchen.example.Vacationater.database.Repository;
import skirchen.example.Vacationater.entity.Excursion;
import skirchen.example.Vacationater.entity.Vacation;

import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView titleTextView;
        public TextView lodgingTextView;
        public TextView startDateTextView;
        public TextView endDateTextView;
        public TextView varText;
        public RecyclerView excursions;
        public ViewHolder(View itemView){
            super(itemView);
            titleTextView = (TextView) itemView.findViewById((R.id.report_vacation_title));
            lodgingTextView = (TextView) itemView.findViewById(R.id.report_vacation_lodging);
            startDateTextView = (TextView) itemView.findViewById((R.id.report_vacation_start_date));
            endDateTextView = (TextView) itemView.findViewById(R.id.report_vacation_end_date);
            varText = itemView.findViewById(R.id.report_excursionTV);
            excursions = itemView.findViewById(R.id.report_excursionRV);
        }

    }

    private List<Vacation> mVacations;
    private Application application;
    Repository repo;
    Context context;

    public ReportAdapter(List<Vacation> vacations, Application application){
        mVacations = vacations;
        this.application = application;
        repo = new Repository(application);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View reportView = inflater.inflate(R.layout.report_item, parent, false);
        return new ViewHolder(reportView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Vacation vacation = mVacations.get(position);
        List<Excursion> excursions = repo.getAllExcursions(vacation.getVacationId());
        TextView textView = holder.titleTextView;
        textView.setText(vacation.getTitle());
        textView = holder.lodgingTextView;
        textView.setText((vacation.getLodging()));
        textView = holder.startDateTextView;
        textView.setText(vacation.getStartDate());
        textView = holder.endDateTextView;
        textView.setText(vacation.getEndDate());
        textView = holder.varText;
        textView.setText(excursions.isEmpty() ? "" : "Excursions");
        ExcursionAdapter excursionAdapter = new ExcursionAdapter(excursions);
        holder.excursions.setAdapter(excursionAdapter);
        holder.excursions.setLayoutManager(new LinearLayoutManager(context));

        holder.itemView.setOnClickListener((view)->{
            Intent intent = new Intent(view.getContext(), VacationDetail.class);
            intent.putExtra("updating", true);
            intent.putExtra("vacationId", vacation.getVacationId());
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mVacations.size();
    }

    public void filterList(List<Vacation> filteredVacations){
        this.mVacations = filteredVacations;
    }
}
