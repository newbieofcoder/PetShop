package hoanglv.fpoly.petshop.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hoanglv.fpoly.petshop.DTO.Bill;
import hoanglv.fpoly.petshop.DTO.BillDetails;
import hoanglv.fpoly.petshop.DTO.Pets;
import hoanglv.fpoly.petshop.R;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private Context context;
    private List<Bill> list;
    private List<Pets> petsList;
    private List<BillDetails> billDetails;
    private final OnCartClickListener listener;

    public interface OnCartClickListener {
        void onDeleteClick(Bill bill);
        void onDetailClick(Bill bill);
    }

    public CartAdapter(Context context, List<Bill> list, List<Pets> petsList, List<BillDetails> billDetails, OnCartClickListener listener) {
        this.context = context;
        this.list = list;
        this.petsList = petsList;
        this.billDetails = billDetails;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {

        for (BillDetails billDetail : billDetails) {
            if (billDetail.getId_bill().equals(list.get(position).get_id())) {
                holder.quantity = billDetail.getQuantity();
                holder.id = billDetail.getId_pet();
                break;
            }
        }
        for (Pets pet : petsList) {
            if (pet.get_id().equals(holder.id)) {
                holder.price = pet.getPrice();
                break;
            }
        }
        long total = holder.quantity * holder.price;
        holder.tvDate.setText("Hoá đơn ngày: " + list.get(position).getDate());
        holder.tvTotal.setText("Thành tiền: " + total + " VNĐ");
        holder.lnItemView.setOnClickListener(v -> {
            listener.onDetailClick(list.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvTotal;
        LinearLayout lnItemView;
        long quantity = 0;
        long price = 0;
        String id = "";

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTotal = itemView.findViewById(R.id.tvTotal);
            lnItemView = itemView.findViewById(R.id.lnItemView);
        }
    }
}
