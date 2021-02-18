package me.angelvc.misaes.schedule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import me.angelvc.misaes.R;
import me.angelvc.misaes.databinding.ScheduleRecyclerRowBinding;
import me.angelvc.saes.scraper.models.ScheduleClass;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {

    private List<ScheduleClass> schedule;

    public ScheduleAdapter(List<ScheduleClass> schedule){
        this.schedule = schedule;
    }

    public ScheduleAdapter(){
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.schedule_recycler_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ScheduleClass scheduleClass = schedule.get(position);
        String[] days = scheduleClass.getHorario();
        holder.setupScheduleCard(
                scheduleClass.getMateria(),
                scheduleClass.getGrupo(),
                scheduleClass.getProfesor(),
                scheduleClass.getEdificio(),
                scheduleClass.getSalon(),
                days[0],
                days[1],
                days[2],
                days[3],
                days[4]
        );
    }

    @Override
    public int getItemCount() {
        return schedule == null? 0: schedule.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ScheduleRecyclerRowBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ScheduleRecyclerRowBinding.bind(itemView);
        }

        private void setupScheduleCard(String subject, String groupId, String professor,
                                       String building, String classroom, String monday,
                                       String tuesday, String wednesday, String thursday,
                                       String friday){
            binding.subject.setText(subject);
            binding.groupId.setText(groupId);
            binding.professor.setText(professor);
            binding.building.setText(building);
            binding.classroom.setText(classroom);
            binding.monday.setText(monday);
            binding.tuesday.setText(tuesday);
            binding.wednesday.setText(wednesday);
            binding.thursday.setText(thursday);
            binding.friday.setText(friday);
        }
    }
}
