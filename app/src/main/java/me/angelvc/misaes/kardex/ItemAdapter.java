package me.angelvc.misaes.kardex;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import me.angelvc.misaes.R;
import me.angelvc.misaes.databinding.KardexRecyclerRowBinding;
import me.angelvc.saes.scraper.models.KardexClass;

public class ItemAdapter  extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private final List<KardexClass> classes;

    public ItemAdapter(List<KardexClass> classes){
        this.classes = classes;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kardex_recycler_row,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        KardexClass kardexClass = classes.get(position);

        holder.binding.date.setText(kardexClass.getDate());
        holder.binding.evalType.setText( kardexClass.getEvaluationType());
        holder.binding.finalGrade.setText( kardexClass.getGrade());
        holder.binding.subject.setText( kardexClass.getName() );
        holder.binding.groupId.setText( kardexClass.getCode());
        holder.binding.term.setText( kardexClass.getTerm());
    }

    @Override
    public int getItemCount() {
        return classes==null ? 0 : classes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final KardexRecyclerRowBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = KardexRecyclerRowBinding.bind(itemView);
        }
    }

}
