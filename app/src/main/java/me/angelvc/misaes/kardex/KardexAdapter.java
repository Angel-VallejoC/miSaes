package me.angelvc.misaes.kardex;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import me.angelvc.misaes.R;
import me.angelvc.misaes.databinding.KardexSectionRowBinding;
import me.angelvc.saes.scraper.models.Kardex;

public class KardexAdapter extends RecyclerView.Adapter<KardexAdapter.ViewHolder> {

    private List<KardexSectionModel> sections;

    public KardexAdapter(Kardex kardex){
        sections = new ArrayList<>();
        int size = kardex.size();

        for (int i = 1; i <= size; i++){
            sections.add(new KardexSectionModel(kardex.getLevelClasses(i)));
        }
    }

    public KardexAdapter(){}


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.kardex_section_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String sectionTitle = "";
        if (position==0)            sectionTitle = holder.binding.getRoot().getContext().getString(R.string.kardex_first_section);
        else if (position == 1)     sectionTitle = holder.binding.getRoot().getContext().getString(R.string.kardex_second_section);
        else if (position == 2)     sectionTitle = holder.binding.getRoot().getContext().getString(R.string.kardex_third_section);
        else if (position == 3)     sectionTitle = holder.binding.getRoot().getContext().getString(R.string.kardex_fourth_section);
        else if (position == 4)     sectionTitle = holder.binding.getRoot().getContext().getString(R.string.kardex_fifth_section);
        else if (position == 5)     sectionTitle = holder.binding.getRoot().getContext().getString(R.string.kardex_sixth_section);
        else if (position == 6)     sectionTitle = holder.binding.getRoot().getContext().getString(R.string.kardex_seventh_section);

        holder.binding.sectionTitle.setText(sectionTitle);

        KardexSectionModel model = sections.get(position);
        boolean isExpanded = model.isExpanded();

        holder.binding.kardexGradesRecycler.setVisibility(isExpanded? View.VISIBLE : View.GONE);
        holder.binding.toggleArrow.setImageResource(isExpanded? R.drawable.ic_kardex_arrow_up: R.drawable.ic_kardex_arrow_down);

        ItemAdapter nestedAdapter = new ItemAdapter(model.getClasses());

        holder.binding.kardexGradesRecycler.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.binding.kardexGradesRecycler.setAdapter(nestedAdapter);
        holder.binding.container.setOnClickListener(view -> {
            model.setExpanded(!model.isExpanded());
            notifyItemChanged(holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return sections == null? 0: sections.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final KardexSectionRowBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = KardexSectionRowBinding.bind(itemView);
        }
    }
}
