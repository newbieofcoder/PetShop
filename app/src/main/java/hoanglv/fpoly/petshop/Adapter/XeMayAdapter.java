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

import hoanglv.fpoly.petshop.DTO.XeMay;
import hoanglv.fpoly.petshop.R;

public class XeMayAdapter extends RecyclerView.Adapter<XeMayAdapter.PetViewHolder> {
    private List<XeMay> xeMayList;
    private final OnXeMayClickListener listener;
    private Context context;

    public XeMayAdapter(Context context, List<XeMay> xeMayList, OnXeMayClickListener listener) {
        this.context = context;
        this.xeMayList = xeMayList;
        this.listener = listener;
    }

    public interface OnXeMayClickListener {
        void onXeMayClick(XeMay xeMay);
        void onLongClick(XeMay xeMay);
        void onDeleteClick(XeMay xeMay);
    }

    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_xe_may, parent, false);
        return new PetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {
        holder.xeMayImage.setImageResource(R.drawable.dog);
        holder.xeMayName.setText(xeMayList.get(position).getTenXe());
        holder.xeMayPrice.setText(String.valueOf(xeMayList.get(position).getGiaBan()));
        holder.xeMayColor.setText(xeMayList.get(position).getMauSac());
        holder.delete.setOnClickListener(v -> {
            listener.onDeleteClick(xeMayList.get(position));
        });
        holder.itemView.setOnClickListener(v -> listener.onXeMayClick(xeMayList.get(position)));
        holder.itemView.setOnLongClickListener(v -> {
            listener.onLongClick(xeMayList.get(position));
            return true;
        });
    }


    @Override
    public int getItemCount() {
        return xeMayList.size();
    }

    public static class PetViewHolder extends RecyclerView.ViewHolder {
        ImageView xeMayImage;
        TextView xeMayName;
        TextView xeMayPrice;
        TextView xeMayColor;
        ImageView delete;

        PetViewHolder(@NonNull View itemView) {
            super(itemView);
            xeMayImage = itemView.findViewById(R.id.xe_may_image_1);
            xeMayName = itemView.findViewById(R.id.txt_xe_may_name);
            xeMayPrice = itemView.findViewById(R.id.txt_xe_may_price);
            xeMayColor = itemView.findViewById(R.id.txt_xe_may_color);
            delete = itemView.findViewById(R.id.delete);

        }
    }
}

