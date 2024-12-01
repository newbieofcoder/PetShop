package hoanglv.fpoly.petshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hoanglv.fpoly.petshop.DTO.Pets;
import hoanglv.fpoly.petshop.R;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.PetViewHolder> {
    private List<Pets> petList;
    private final OnPetClickListener listener;
    private Context context;

    public PetAdapter(Context context, List<Pets> petList, OnPetClickListener listener) {
        this.context = context;
        this.petList = petList;
        this.listener = listener;
    }

    public interface OnPetClickListener {
        void onPetClick(Pets pet);
        void onLongClick(Pets pet);
        void onDeleteClick(Pets pet);
    }

    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pet_grid, parent, false);
        return new PetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {
        holder.petImage.setImageResource(R.drawable.dog);
        holder.petName.setText(petList.get(position).getName());
        holder.petPrice.setText(String.valueOf(petList.get(position).getPrice()));
        holder.delete.setOnClickListener(v -> {
            listener.onDeleteClick(petList.get(position));
        });
        holder.itemView.setOnClickListener(v -> listener.onPetClick(petList.get(position)));
        holder.itemView.setOnLongClickListener(v -> {
            listener.onLongClick(petList.get(position));
            return true;
        });
    }


    @Override
    public int getItemCount() {
        return petList.size();
    }

    public static class PetViewHolder extends RecyclerView.ViewHolder {
        private final ImageView petImage;
        private final TextView petName;
        private final TextView petPrice;
        private final ImageView delete;

        PetViewHolder(@NonNull View itemView) {
            super(itemView);
            petImage = itemView.findViewById(R.id.pet_image_1);
            petName = itemView.findViewById(R.id.txt_pet_name);
            petPrice = itemView.findViewById(R.id.txt_pet_price);
            delete = itemView.findViewById(R.id.delete);

        }
    }
}

