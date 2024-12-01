package hoanglv.fpoly.petshop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import hoanglv.fpoly.petshop.DTO.Bill;
import hoanglv.fpoly.petshop.DTO.BillDetails;
import hoanglv.fpoly.petshop.DTO.Pets;
import hoanglv.fpoly.petshop.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillDetail extends AppCompatActivity {
    private TextView txtEmail, txtDate, txtPetName, txtPrice, txtQuantity, txtTotal;
    private ImageView back;
    private Button pay_button;
    Bill bill;
    List<BillDetails> billDetails;
    List<Pets> petsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_detail);
        txtEmail = findViewById(R.id.txtEmail);
        txtDate = findViewById(R.id.txtDate);
        txtPetName = findViewById(R.id.txtPetName);
        txtPrice = findViewById(R.id.txtPrice);
        txtQuantity = findViewById(R.id.txtQuantity);
        txtTotal = findViewById(R.id.txtTotal);
        back = findViewById(R.id.back);
        pay_button = findViewById(R.id.pay_button);
        back.setOnClickListener(v -> finish());

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            bill = (Bill) bundle.getSerializable("bill");
            billDetails = (List<BillDetails>) bundle.getSerializable("billDetails");
            petsList = (List<Pets>) bundle.getSerializable("petsList");
            if (bill != null && billDetails != null && petsList != null) {
                txtEmail.setText("Email: " + bill.getEmail());
                txtDate.setText("Ngày: " + bill.getDate());
                for (BillDetails billDetail : billDetails) {
                    if (billDetail.getId_bill().equals(bill.get_id())) {
                        for (Pets pet : petsList) {
                            if (pet.get_id().equals(billDetail.getId_pet())) {
                                txtPetName.setText("Tên thú cưng: " + pet.getName());
                                txtPrice.setText("Giá: " + pet.getPrice() + " VNĐ");
                                txtQuantity.setText("Số lượng: " + billDetail.getQuantity());
                                txtTotal.setText((billDetail.getQuantity() * pet.getPrice()) + " VNĐ");
                                break;
                            }
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
            }
        }

        pay_button.setOnClickListener(v -> {
            Bundle bundle1 = new Bundle();
            bundle1.putSerializable("item", bill);
            Intent intent1 = new Intent(BillDetail.this, Location.class);
            intent1.putExtras(bundle1);
            startActivity(intent1);
        });
    }
}