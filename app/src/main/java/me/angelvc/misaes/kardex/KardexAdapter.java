package me.angelvc.misaes.kardex;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import me.angelvc.misaes.R;
import me.angelvc.misaes.databinding.KardexRecyclerRowBinding;
import me.angelvc.saes.scraper.models.Kardex;
import me.angelvc.saes.scraper.models.KardexClass;

public class KardexAdapter extends RecyclerView.Adapter<KardexAdapter.ViewHolder> {

    private ArrayList<KardexClass> kardexClasses;

    public KardexAdapter(Kardex kardex){
        kardexClasses = new ArrayList<>();
        kardex.getLevels();
        for (Integer level: kardex.getLevels()){
            ArrayList<KardexClass> entry = kardex.getLevelClasses(level);
            kardexClasses.addAll(entry);
        }
    }

    public KardexAdapter(){

    }

    public void update(Kardex kardex){
        kardexClasses = new ArrayList<>();
        kardex.getLevels();
        for (Integer level: kardex.getLevels()){
            ArrayList<KardexClass> entry = kardex.getLevelClasses(level);
            kardexClasses.addAll(entry);
        }
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.kardex_recycler_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        KardexClass kardexClass = kardexClasses.get(position);

        holder.binding.date.setText(kardexClass.getDate());
        holder.binding.evalType.setText( kardexClass.getEvaluationType());
        holder.binding.finalGrade.setText( kardexClass.getGrade());
        holder.binding.subject.setText( kardexClass.getName() );
        holder.binding.groupId.setText( kardexClass.getCode());
        holder.binding.term.setText( kardexClass.getTerm());
    }

    @Override
    public int getItemCount() {
        return kardexClasses == null? 0: kardexClasses.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        private KardexRecyclerRowBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = KardexRecyclerRowBinding.bind(itemView);
        }
    }
}
