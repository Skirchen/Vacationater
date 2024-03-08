package skirchen.example.Vacationater.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import skirchen.example.Vacationater.R;
import skirchen.example.Vacationater.entity.Excursion;

public class ExcursionAdapter extends RecyclerView.Adapter<ExcursionAdapter.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView excursionTitle;
        TextView excursionDate;
        public ViewHolder(View itemView){
            super(itemView);
            excursionTitle = itemView.findViewById(R.id.excursion_title);
            excursionDate = itemView.findViewById(R.id.excursion_date);
        }

    }

    Context context;
    List<Excursion> mExcusrions;
    String vacStartDate;
    String vacEndDate;
    public ExcursionAdapter(List<Excursion> excursions, String vacStartDate, String vacEndDate){
        this.mExcusrions = excursions;
        this.vacStartDate = vacStartDate;
        this.vacEndDate = vacEndDate;
    }
    public ExcursionAdapter(List<Excursion> excursions){
        this.mExcusrions = excursions;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View excursionView = inflater.inflate(R.layout.excursion_item, parent, false);
        return new ViewHolder(excursionView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Excursion excursion = mExcusrions.get(position);
        TextView textView = holder.excursionTitle;
        textView.setText(excursion.getTitle());
        textView = holder.excursionDate;
        textView.setText(excursion.getDate());

        holder.itemView.setOnClickListener((view)->{
            Intent intent = new Intent(this.context, ExcursionDetail.class);
            intent.putExtra("updating", true);
            intent.putExtra("excursionId", excursion.getExcursionId());
            intent.putExtra("vacationId", excursion.getVacationId());
            intent.putExtra("title", excursion.getTitle());
            intent.putExtra("date", excursion.getDate());
            intent.putExtra("startDate", vacStartDate);
            intent.putExtra("endDate", vacEndDate);
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mExcusrions.size();
    }


}
