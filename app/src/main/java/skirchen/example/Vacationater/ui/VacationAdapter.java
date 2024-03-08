package skirchen.example.Vacationater.ui;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import skirchen.example.Vacationater.R;
import skirchen.example.Vacationater.entity.Vacation;

import java.util.List;

public class VacationAdapter extends RecyclerView.Adapter<VacationAdapter.ViewHolder> {


    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView titleTextView;
        public TextView lodgingTextView;
        public TextView startDateTextView;
        public TextView endDateTextView;
        public ImageButton editButton;
        public ImageButton deleteButton;
        public ViewHolder(View itemView){
            super(itemView);
            titleTextView = (TextView) itemView.findViewById((R.id.vacation_title));
            startDateTextView = (TextView) itemView.findViewById((R.id.vacation_start_date));
            endDateTextView = (TextView) itemView.findViewById(R.id.vacation_end_date);
        }

    }

    private List<Vacation> mVacations;
    private Application application;
    Context context;

    public VacationAdapter(List<Vacation> vacations, Application application){
        mVacations = vacations;
        application = application;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View vacationView = inflater.inflate(R.layout.vacation_item, parent, false);
        return new ViewHolder(vacationView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Vacation vacation = mVacations.get(position);
        TextView textView = holder.titleTextView;
        textView.setText(vacation.getTitle());
        textView = holder.startDateTextView;
        textView.setText(vacation.getStartDate());
        textView = holder.endDateTextView;
        textView.setText(vacation.getEndDate());

        holder.itemView.setOnClickListener((view)->{
            Intent intent = new Intent(view.getContext(), VacationDetail.class);
            intent.putExtra("updating", true);
            intent.putExtra("vacationId", vacation.getVacationId());
//            intent.putExtra("title", vacation.getTitle());
//            intent.putExtra("lodging", vacation.getLodging());
//            intent.putExtra("startDate", vacation.getStartDate());
//            intent.putExtra("endDate", vacation.getEndDate());
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
