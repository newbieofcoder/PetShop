package hoanglv.fpoly.petshop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import hoanglv.fpoly.petshop.DTO.Bill;
import hoanglv.fpoly.petshop.DTO.BillDetails;
import hoanglv.fpoly.petshop.DTO.Pets;
import hoanglv.fpoly.petshop.R;
import hoanglv.fpoly.petshop.services.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PetInformation extends AppCompatActivity {
    private Pets pet;
    private TextView name;
    private TextView description;
    private TextView price;
    private Button add_to_cart_button;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private ImageView up, down, back_to_home;
    private TextView quantity;
    private List<Bill> billList;
    private List<BillDetails> billDetails;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(APIService.DOMAIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    APIService apiService = retrofit.create(APIService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        pet = (Pets) bundle.get("pet");
        name = findViewById(R.id.detail_name);
        description = findViewById(R.id.detail_description);
        price = findViewById(R.id.detail_price);
        add_to_cart_button = findViewById(R.id.add_to_cart_button);
        back_to_home = findViewById(R.id.back_to_home);
        quantity = findViewById(R.id.quantity);
        up = findViewById(R.id.up);
        down = findViewById(R.id.down);

        back_to_home.setOnClickListener(v -> finish());
        up.setOnClickListener(v -> {
            int i = Integer.parseInt(quantity.getText().toString());
            if (i < 10) {
                i++;
                if (i < 10) {
                    quantity.setText("0" + i);
                } else {
                    quantity.setText(String.valueOf(i));
                }
                price.setText(String.valueOf(pet.getPrice() * i));
            }
        });
        down.setOnClickListener(v -> {
            int i = Integer.parseInt(quantity.getText().toString());
            if (i > 0) {
                i--;
                if (i < 10) {
                    quantity.setText("0" + i);
                } else {
                    quantity.setText(String.valueOf(i));
                }
                price.setText(String.valueOf(pet.getPrice() * i));
            }
        });

        name.setText(pet.getName());
        description.setText(pet.getDescription());


        add_to_cart_button.setOnClickListener(v -> {
            if (Integer.parseInt(quantity.getText().toString()) == 0){
                Toast.makeText(PetInformation.this, "Số lượng sản phẩm phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                return;
            }
            LocalDate now = LocalDate.now();
            String email = Objects.requireNonNull(auth.getCurrentUser()).getEmail();
            Bill bill = new Bill(now.toString(), email);
            Call<List<Bill>> callAddToCart = apiService.addToCart(bill);
            callAddToCart.enqueue(new Callback<List<Bill>>() {
                @Override
                public void onResponse(Call<List<Bill>> call, Response<List<Bill>> response) {
                    if (response.isSuccessful()) {
                        billList = response.body();
                        BillDetails details = new BillDetails(billList.get(billList.size()-1).get_id(), pet.get_id(), Integer.parseInt(quantity.getText().toString()));
                        Call<List<BillDetails>> callAddBillDetail = apiService.addBillDetails(details);
                        callAddBillDetail.enqueue(new Callback<List<BillDetails>>() {
                            @Override
                            public void onResponse(Call<List<BillDetails>> call, Response<List<BillDetails>> response) {
                                if (response.isSuccessful()) {
                                    billDetails = response.body();
                                    Toast.makeText(PetInformation.this, "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(PetInformation.this, "Lỗi khi thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<List<BillDetails>> call, Throwable throwable) {

                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<List<Bill>> call, Throwable throwable) {
                    Log.e("Them", "onFailure: " + throwable.getMessage());
                }
            });
        });
    }
}