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

import hoanglv.fpoly.petshop.DTO.DienThoai_07122024;
import hoanglv.fpoly.petshop.R;

public class XeMayAdapter extends RecyclerView.Adapter<XeMayAdapter.PetViewHolder> {
    private List<DienThoai_07122024> xeDienThoaiList;
    private final OnDienThoaiClickListener listener;
    private Context context;

    public XeMayAdapter(Context context, List<DienThoai_07122024> xeDienThoaiList, OnDienThoaiClickListener listener) {
        this.context = context;
        this.xeDienThoaiList = xeDienThoaiList;
        this.listener = listener;
    }

    public interface OnDienThoaiClickListener {
        void onDienThoaiClick(DienThoai_07122024 xeMay);
        void onDeleteClick(DienThoai_07122024 xeMay);
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
        holder.xeMayName.setText(xeDienThoaiList.get(position).getTenDienThoai());
        holder.xeMayPrice.setText(String.valueOf(xeDienThoaiList.get(position).getTrangThai()));
        holder.xeMayColor.setText(xeDienThoaiList.get(position).getNgayNhap());
        holder.delete.setOnClickListener(v -> {
            listener.onDeleteClick(xeDienThoaiList.get(position));
        });
        holder.itemView.setOnClickListener(v -> listener.onDienThoaiClick(xeDienThoaiList.get(position)));
    }


    @Override
    public int getItemCount() {
        return xeDienThoaiList.size();
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

