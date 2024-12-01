package hoanglv.fpoly.petshop.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.auth.FirebaseAuth;

import hoanglv.fpoly.petshop.Adapter.PetAdapter;
import hoanglv.fpoly.petshop.DTO.Bill;
import hoanglv.fpoly.petshop.DTO.BillDetails;
import hoanglv.fpoly.petshop.DTO.Pets;
import hoanglv.fpoly.petshop.services.APIService;
import hoanglv.fpoly.petshop.R;
import hoanglv.fpoly.petshop.ui.Cart;
import hoanglv.fpoly.petshop.ui.PetInformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.Serializable;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private PetAdapter petAdapter;
    private List<Pets> petsList;
    private List<BillDetails> billDetails;
    private List<Bill> billList;
    private ImageView imgAdd, imgCart;
    private EditText txtSearch;
    private final FirebaseAuth auth = FirebaseAuth.getInstance();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(APIService.DOMAIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    APIService apiService = retrofit.create(APIService.class);

    @Override
    public void onResume() {
        super.onResume();
        getBillDetails();
        getBills();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getBillDetails();
        getBills();
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        imgAdd = view.findViewById(R.id.imgadd);
        txtSearch = view.findViewById(R.id.txtSearch);
        imgCart = view.findViewById(R.id.imgCart);

        imgCart.setOnClickListener(v -> {

            Intent intent = new Intent(getActivity(), Cart.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("billDetails", (Serializable) billDetails);
            bundle.putSerializable("petsList", (Serializable) petsList);
            intent.putExtras(bundle);
            startActivity(intent);
        });

        txtSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String key = txtSearch.getText().toString();
                Call<List<Pets>> callSearch = apiService.search(key);
                callSearch.enqueue(new Callback<List<Pets>>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(Call<List<Pets>> call, Response<List<Pets>> response) {
                        if (response.isSuccessful()) {
                            petsList.clear();
                            petsList.addAll(response.body());
                            petAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), "Lỗi khi tìm kiếm thú cưng", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Pets>> call, Throwable throwable) {
                        Log.e("Main", "onFailure: " + throwable.getMessage());
                    }
                });
                return true;
            }
            return false;
        });

        imgAdd.setOnClickListener(v -> {
            View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_new_item, null);
            EditText edtName = view1.findViewById(R.id.edt_name);
            EditText edtDescription = view1.findViewById(R.id.edt_description);
            EditText edtPrice = view1.findViewById(R.id.edt_price);
            EditText edtSpecies = view1.findViewById(R.id.edt_species);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                    .setView(view1)
                    .setCancelable(true);
            builder.setPositiveButton("OK", (dialog, which) -> {
                String name = edtName.getText().toString();
                String description = edtDescription.getText().toString();
                String price = edtPrice.getText().toString();
                String species = edtSpecies.getText().toString();
                if (!name.isEmpty() && !description.isEmpty() && !price.isEmpty() && !species.isEmpty()) {
                    long priceLong = Long.parseLong(price);
                    Pets newPet = new Pets(name, description, priceLong, species);
                    Call<List<Pets>> callAddPet = apiService.addPet(newPet);
                    callAddPet.enqueue(new Callback<List<Pets>>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onResponse(Call<List<Pets>> call, Response<List<Pets>> response) {
                            if (response.isSuccessful()) {
                                petsList.clear();
                                petsList.addAll(response.body());
                                petAdapter.notifyDataSetChanged();
                                Toast.makeText(getActivity(), "Thêm thú cưng thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Lỗi khi thêm thú cưng", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Pets>> call, Throwable throwable) {
                            Log.e("Them", "onFailure: " + throwable.getMessage());
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> {
                dialog.dismiss();
            });
            Dialog dialog = builder.create();
            dialog.show();
        });

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));

        Call<List<Pets>> call = apiService.getPets();
        call.enqueue(new Callback<List<Pets>>() {
            @Override
            public void onResponse
                    (Call<List<Pets>> call, Response<List<Pets>> response) {
                if (response.isSuccessful()) {
                    petsList = response.body();
                    petAdapter = new PetAdapter(getActivity(), petsList, new PetAdapter.OnPetClickListener() {
                        @Override
                        public void onPetClick(Pets pet) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("pet", pet);
                            Intent intent = new Intent(requireActivity(), PetInformation.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }

                        @Override
                        public void onLongClick(Pets pet) {
                            View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_edit_item, null);
                            EditText edtName = view1.findViewById(R.id.edt_name);
                            EditText edtDescription = view1.findViewById(R.id.edt_description);
                            EditText edtPrice = view1.findViewById(R.id.edt_price);
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                                    .setView(view1)
                                    .setCancelable(true);
                            edtName.setText(pet.getName());
                            edtDescription.setText(pet.getDescription());
                            edtPrice.setText(String.valueOf(pet.getPrice()));
                            builder.setPositiveButton("OK", (dialog, which) -> {
                                String newName = edtName.getText().toString();
                                String newDescription = edtDescription.getText().toString();
                                String newPrice = edtPrice.getText().toString();
                                if (!newName.isEmpty() && !newDescription.isEmpty() && !newPrice.isEmpty()) {
                                    long newPriceLong = Long.parseLong(newPrice);
                                    pet.setName(newName);
                                    pet.setDescription(newDescription);
                                    pet.setPrice(newPriceLong);
                                    Call<List<Pets>> callUpdatePet = apiService.updatePet(pet);
                                    callUpdatePet.enqueue(new Callback<List<Pets>>() {
                                        @SuppressLint("NotifyDataSetChanged")
                                        @Override
                                        public void onResponse(Call<List<Pets>> call, Response<List<Pets>> response) {
                                            if (response.isSuccessful()) {
                                                petsList.clear();
                                                petsList.addAll(response.body());
                                                petAdapter.notifyDataSetChanged();
                                                Toast.makeText(getActivity(), "Cập nhật thú cưng thành công", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(getActivity(), "Lỗi khi cập nhật thú cưng", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<List<Pets>> call, Throwable throwable) {
                                            Log.e("Update", "onFailure: " + throwable.getMessage());
                                        }
                                    });
                                } else {
                                    Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                                }
                            });
                            Dialog dialog = builder.create();
                            dialog.show();
                        }

                        @Override
                        public void onDeleteClick(Pets pet) {
                            String id = pet.get_id();
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                                    .setTitle("Xóa thú cưng?")
                                    .setMessage("Bạn có chắc chắn muốn xóa thú cưng này?")
                                    .setPositiveButton("OK", (dialog, which) -> {
                                        Call<List<Pets>> callDeletePet = apiService.deletePet(id);
                                        callDeletePet.enqueue(new Callback<List<Pets>>() {
                                            @SuppressLint("NotifyDataSetChanged")
                                            @Override
                                            public void onResponse(Call<List<Pets>> call, Response<List<Pets>> response) {
                                                petsList.clear();
                                                petsList.addAll(response.body());
                                                petAdapter.notifyDataSetChanged();
                                                deleteFromBillDetails(id);
                                                Toast.makeText(getActivity(), "Xóa thú cưng thành công", Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onFailure(Call<List<Pets>> call, Throwable throwable) {
                                                Log.e("Xoa", "onFailure: " + throwable.getMessage());
                                            }
                                        });
                                    })
                                    .setNegativeButton("Cancel", (dialog, which) -> {
                                        dialog.dismiss();
                                    });
                            Dialog dialog = builder.create();
                            dialog.show();
                        }
                    });
                    recyclerView.setAdapter(petAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Pets>> call, Throwable throwable) {
                Log.e("GetPet", "onFailure: " + throwable.getMessage());
            }
        });

        return view;
    }

    private void deleteFromBillDetails(String id) {
        String billDetailId = "";
        String billId = "";
        for (BillDetails billDetail : billDetails) {
            if (billDetail.getId_pet().equals(id)) {
                billDetailId = billDetail.get_id();
                billId = billDetail.getId_bill();
                break;
            }
        }
        Call<List<BillDetails>> callDeleteBillDetails = apiService.deleteBillDetails(billDetailId);
        callDeleteBillDetails.enqueue(new Callback<List<BillDetails>>() {
            @Override
            public void onResponse(Call<List<BillDetails>> call, Response<List<BillDetails>> response) {

            }
            @Override
            public void onFailure(Call<List<BillDetails>> call, Throwable throwable) {

            }
        });
        Call<List<Bill>> callDeleteBill = apiService.deleteFromCart(billId);
        callDeleteBill.enqueue(new Callback<List<Bill>>() {
            @Override
            public void onResponse(Call<List<Bill>> call, Response<List<Bill>> response) {

            }

            @Override
            public void onFailure(Call<List<Bill>> call, Throwable throwable) {

            }
        });
    }

    public void getBillDetails() {
        Call<List<BillDetails>> call = apiService.getBillDetails();
        call.enqueue(new Callback<List<BillDetails>>() {
            @Override
            public void onResponse(Call<List<BillDetails>> call, Response<List<BillDetails>> response) {
                if (response.isSuccessful()) {
                    billDetails = response.body();
                }
            }

            @Override
            public void onFailure(Call<List<BillDetails>> call, Throwable throwable) {

            }
        });
    }
    public void getBills() {
        Call<List<Bill>> call = apiService.getCart();
        call.enqueue(new Callback<List<Bill>>() {
            @Override
            public void onResponse(Call<List<Bill>> call, Response<List<Bill>> response) {
                billList = response.body();
            }

            @Override
            public void onFailure(Call<List<Bill>> call, Throwable throwable) {

            }
        });
    }
}
