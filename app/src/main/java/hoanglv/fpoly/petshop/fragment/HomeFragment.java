package hoanglv.fpoly.petshop.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;

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

import hoanglv.fpoly.petshop.Adapter.XeMayAdapter;
import hoanglv.fpoly.petshop.DTO.XeMay;
import hoanglv.fpoly.petshop.services.APIService;
import hoanglv.fpoly.petshop.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private XeMayAdapter xeMayAdapter;
    private List<XeMay> xeMayList;
    private ImageView imgAdd;
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        imgAdd = view.findViewById(R.id.imgadd);
        txtSearch = view.findViewById(R.id.txtSearch);

        txtSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String key = txtSearch.getText().toString();
                Call<List<XeMay>> callSearch = apiService.searchXeMay(key);
                callSearch.enqueue(new Callback<List<XeMay>>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(Call<List<XeMay>> call, Response<List<XeMay>> response) {
                        if (response.isSuccessful()) {
                            xeMayList.clear();
                            xeMayList.addAll(response.body());
                            xeMayAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), "Lỗi khi tìm kiếm thú cưng", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<XeMay>> call, Throwable throwable) {
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
                    XeMay newXemay = new XeMay(name, description, priceLong, species, "");
                    Call<List<XeMay>> callAddPet = apiService.addXeMay(newXemay);
                    callAddPet.enqueue(new Callback<List<XeMay>>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onResponse(Call<List<XeMay>> call, Response<List<XeMay>> response) {
                            if (response.isSuccessful()) {
                                xeMayList.clear();
                                xeMayList.addAll(response.body());
                                xeMayAdapter.notifyDataSetChanged();
                                Toast.makeText(getActivity(), "Thêm xe máy thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Lỗi khi thêm xe máy", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<XeMay>> call, Throwable throwable) {
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

        Call<List<XeMay>> call = apiService.getXeMay();
        call.enqueue(new Callback<List<XeMay>>() {
            @Override
            public void onResponse
                    (Call<List<XeMay>> call, Response<List<XeMay>> response) {
                if (response.isSuccessful()) {
                    xeMayList = response.body();
                    xeMayAdapter = new XeMayAdapter(getActivity(), xeMayList, new XeMayAdapter.OnXeMayClickListener() {
                        @Override
                        public void onXeMayClick(XeMay pet) {
                        }

                        @Override
                        public void onLongClick(XeMay pet) {
                            View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_edit_item, null);
                            EditText edtName = view1.findViewById(R.id.edt_name);
                            EditText edtDescription = view1.findViewById(R.id.edt_description);
                            EditText edtPrice = view1.findViewById(R.id.edt_price);
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                                    .setView(view1)
                                    .setCancelable(true);
                            edtName.setText(pet.getTenXe());
                            edtDescription.setText(pet.getMauSac());
                            edtPrice.setText(String.valueOf(pet.getGiaBan()));
                            builder.setPositiveButton("OK", (dialog, which) -> {
                                String newName = edtName.getText().toString();
                                String newDescription = edtDescription.getText().toString();
                                String newPrice = edtPrice.getText().toString();
                                if (!newName.isEmpty() && !newDescription.isEmpty() && !newPrice.isEmpty()) {
                                    long newPriceLong = Long.parseLong(newPrice);
                                    pet.setTenXe(newName);
                                    pet.setMauSac(newDescription);
                                    pet.setGiaBan(newPriceLong);
                                    Call<List<XeMay>> callUpdatePet = apiService.updateXeMay(pet);
                                    callUpdatePet.enqueue(new Callback<List<XeMay>>() {
                                        @SuppressLint("NotifyDataSetChanged")
                                        @Override
                                        public void onResponse(Call<List<XeMay>> call, Response<List<XeMay>> response) {
                                            if (response.isSuccessful()) {
                                                xeMayList.clear();
                                                xeMayList.addAll(response.body());
                                                xeMayAdapter.notifyDataSetChanged();
                                                Toast.makeText(getActivity(), "Cập nhật thú cưng thành công", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(getActivity(), "Lỗi khi cập nhật thú cưng", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<List<XeMay>> call, Throwable throwable) {
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
                        public void onDeleteClick(XeMay pet) {
                            String id = pet.get_id();
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                                    .setTitle("Xóa thú cưng?")
                                    .setMessage("Bạn có chắc chắn muốn xóa thú cưng này?")
                                    .setPositiveButton("OK", (dialog, which) -> {
                                        Call<List<XeMay>> callDeletePet = apiService.deleteXeMay(id);
                                        callDeletePet.enqueue(new Callback<List<XeMay>>() {
                                            @SuppressLint("NotifyDataSetChanged")
                                            @Override
                                            public void onResponse(Call<List<XeMay>> call, Response<List<XeMay>> response) {
                                                xeMayList.clear();
                                                xeMayList.addAll(response.body());
                                                xeMayAdapter.notifyDataSetChanged();
                                                Toast.makeText(getActivity(), "Xóa xe máy thành công", Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onFailure(Call<List<XeMay>> call, Throwable throwable) {
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
                    recyclerView.setAdapter(xeMayAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<XeMay>> call, Throwable throwable) {
                Log.e("GetXeMay", "onFailure: " + throwable.getMessage());
            }
        });

        return view;
    }
}
