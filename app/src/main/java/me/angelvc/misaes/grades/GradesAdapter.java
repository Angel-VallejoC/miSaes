package me.angelvc.misaes.grades;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import me.angelvc.misaes.R;
import me.angelvc.misaes.databinding.GradesRecyclerRowBinding;
import me.angelvc.saes.scraper.models.GradeEntry;

public class GradesAdapter extends RecyclerView.Adapter<GradesAdapter.ViewHolder> {

    private ArrayList<GradeEntry> grades;

    public GradesAdapter(ArrayList<GradeEntry> grades){
        this.grades = grades;
    }

    public GradesAdapter(){
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grades_recycler_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GradeEntry gradeEntry = grades.get(position);
        holder.setupGradesCard(
                gradeEntry.getMateria(), gradeEntry.getGrupo(), gradeEntry.getCalFinal(),
                gradeEntry.getPrimero(), gradeEntry.getSegundo(), gradeEntry.getTercero(),
                gradeEntry.getExtra()
        );
    }

    @Override
    public int getItemCount() {
        return grades == null? 0: grades.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private GradesRecyclerRowBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = GradesRecyclerRowBinding.bind(itemView);
        }

        private void setupGradesCard(String subject, String groupId, String finalGrade,
                                String first, String second, String third, String extra){
            binding.subject.setText(subject);
            binding.groupId.setText(groupId);
            binding.finalGrade.setText(finalGrade);
            binding.firstGrade.setText(first);
            binding.secondGrade.setText(second);
            binding.thirdGrade.setText(third);
            binding.extraGrade.setText(extra);
        }
    }
}
