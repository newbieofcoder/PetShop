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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import hoanglv.fpoly.petshop.Adapter.PetAdapter;
import hoanglv.fpoly.petshop.DTO.Pets;
import hoanglv.fpoly.petshop.services.APIService;
import hoanglv.fpoly.petshop.R;
import hoanglv.fpoly.petshop.ui.Profile;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private PetAdapter petAdapter;
    private ImageView imgProfile;
    private List<Pets> petsList;
    private ImageView imgAdd;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(APIService.DOMAIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    APIService apiService = retrofit.create(APIService.class);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        imgProfile = view.findViewById(R.id.imgProfile);
        imgAdd = view.findViewById(R.id.imgadd);
        imgAdd.setOnClickListener(v -> {
            View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_new_item, null);
            EditText edtName = view1.findViewById(R.id.edt_name);
            EditText edtDescription = view1.findViewById(R.id.edt_description);
            EditText edtPrice = view1.findViewById(R.id.edt_price);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                    .setView(view1)
                    .setCancelable(true);
            builder.setPositiveButton("OK", (dialog, which) -> {
                String name = edtName.getText().toString();
                String description = edtDescription.getText().toString();
                String price = edtPrice.getText().toString();
                if (!name.isEmpty() && !description.isEmpty() && !price.isEmpty()) {
                    long priceLong = Long.parseLong(price);
                    Pets newPet = new Pets(name, description, priceLong, "");
                    Call<List<Pets>> callAddPet = apiService.addPet(newPet);
                    callAddPet.enqueue(new Callback<List<Pets>>() {
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
                            Log.e("Main", "onFailure: " + throwable.getMessage());
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
            public void onResponse(Call<List<Pets>> call, Response<List<Pets>> response) {
                if (response.isSuccessful()) {
                    petsList = response.body();
                    petAdapter = new PetAdapter(getActivity(), petsList, new PetAdapter.OnPetClickListener() {
                        @Override
                        public void onPetClick(Pets pet) {
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
                                        @Override
                                        public void onResponse(Call<List<Pets>> call, Response<List<Pets>> response) {
                                            if (response.isSuccessful()){
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
                        public void onLongClick(Pets pet) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                                    .setTitle("Xóa thú cưng?")
                                    .setMessage("Bạn có chắc chắn muốn xóa thú cưng này?")
                                    .setPositiveButton("OK", (dialog, which) -> {
                                        Log.e("Main", "onLongClick: " + pet.get_id());
                                        Call<List<Pets>> callDeletePet = apiService.deletePet(pet.get_id());
                                        callDeletePet.enqueue(new Callback<List<Pets>>() {
                                            @Override
                                            public void onResponse(Call<List<Pets>> call, Response<List<Pets>> response) {
                                                petsList.clear();
                                                petsList.addAll(response.body());
                                                petAdapter.notifyDataSetChanged();
                                                Toast.makeText(getActivity(), "Xóa thú cưng thành công", Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onFailure(Call<List<Pets>> call, Throwable throwable) {
                                                Log.e("Main", "onFailure: " + throwable.getMessage());
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
                Log.e("Main", "onFailure: " + throwable.getMessage());
            }
        });


        imgProfile.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Profile.class);
            startActivity(intent);
        });

        return view;
    }
}
