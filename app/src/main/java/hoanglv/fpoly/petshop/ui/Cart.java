package hoanglv.fpoly.petshop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import hoanglv.fpoly.petshop.Adapter.CartAdapter;
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

public class Cart extends AppCompatActivity {
    private CartAdapter cartAdapter;
    private RecyclerView rvCart;
    private List<Bill> billList;
    private List<Pets> petsList = new ArrayList<>();
    private List<BillDetails> billDetails = new ArrayList<>();
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private ImageView imgback;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(APIService.DOMAIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    APIService apiService = retrofit.create(APIService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        rvCart = findViewById(R.id.rvCart);
        imgback = findViewById(R.id.back_to_home);
        imgback.setOnClickListener(v -> finish());
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            billDetails = (List<BillDetails>) bundle.getSerializable("billDetails");
            petsList = (List<Pets>) bundle.getSerializable("petsList");
            billList = (List<Bill>) bundle.getSerializable("billList");
        }

        Call<List<Bill>> callBill = apiService.getCart();
        callBill.enqueue(new Callback<List<Bill>>() {
            @Override
            public void onResponse(Call<List<Bill>> call, Response<List<Bill>> response) {
                if (response.isSuccessful()) {
                    billList = response.body();
                    cartAdapter = new CartAdapter(Cart.this, billList, petsList, billDetails, new CartAdapter.OnCartClickListener() {
                        @Override
                        public void onDeleteClick(Bill bill) {

                        }

                        @Override
                        public void onDetailClick(Bill bill) {
                            Intent intent1 = new Intent(Cart.this, BillDetail.class);
                            Bundle bundle1 = new Bundle();
                            bundle1.putSerializable("bill", bill);
                            bundle1.putSerializable("billDetails", (Serializable) billDetails);
                            bundle1.putSerializable("petsList", (Serializable) petsList);
                            intent1.putExtras(bundle1);
                            startActivity(intent1);
                        }
                    });
                    LinearLayoutManager manager = new LinearLayoutManager(Cart.this);
                    rvCart.setLayoutManager(manager);
                    rvCart.setAdapter(cartAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Bill>> call, Throwable throwable) {
                Log.e("Message", "onFailure: " + throwable.getMessage());
            }
        });

    }
}