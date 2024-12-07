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
import hoanglv.fpoly.petshop.DTO.DienThoai_07122024;
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
    private List<DienThoai_07122024> xeMayList;
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
                Call<List<DienThoai_07122024>> callSearch = apiService.searchXeMay(key);
                callSearch.enqueue(new Callback<List<DienThoai_07122024>>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(Call<List<DienThoai_07122024>> call, Response<List<DienThoai_07122024>> response) {
                        if (response.isSuccessful()) {
                            xeMayList.clear();
                            xeMayList.addAll(response.body());
                            xeMayAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), "Lỗi khi tìm kiếm thú cưng", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<DienThoai_07122024>> call, Throwable throwable) {
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
            EditText edtColor = view1.findViewById(R.id.edt_color);
            EditText edtPrice = view1.findViewById(R.id.edt_price);
            EditText edtDescription = view1.findViewById(R.id.edt_description);
            EditText edtImage = view1.findViewById(R.id.edt_image);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                    .setView(view1)
                    .setCancelable(true);
            builder.setPositiveButton("OK", (dialog, which) -> {
                String name = edtName.getText().toString();
                String color = edtColor.getText().toString();
                String description = edtDescription.getText().toString();
                String price = edtPrice.getText().toString();
                String image = edtImage.getText().toString();
                if (!name.isEmpty() && !description.isEmpty() && !price.isEmpty() && !color.isEmpty()) {
                    long priceLong = Long.parseLong(price);
                    DienThoai_07122024 newXemay = new DienThoai_07122024(name, color, priceLong, description, "");
                    Call<List<DienThoai_07122024>> callAddPet = apiService.addXeMay(newXemay);
                    callAddPet.enqueue(new Callback<List<DienThoai_07122024>>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onResponse(Call<List<DienThoai_07122024>> call, Response<List<DienThoai_07122024>> response) {
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
                        public void onFailure(Call<List<DienThoai_07122024>> call, Throwable throwable) {
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

        Call<List<DienThoai_07122024>> call = apiService.getXeMay();
        call.enqueue(new Callback<List<DienThoai_07122024>>() {
            @Override
            public void onResponse
                    (Call<List<DienThoai_07122024>> call, Response<List<DienThoai_07122024>> response) {
                if (response.isSuccessful()) {
                    xeMayList = response.body();
                    xeMayAdapter = new XeMayAdapter(getActivity(), xeMayList, new XeMayAdapter.OnDienThoaiClickListener() {
                        @Override
                        public void onDienThoaiClick(DienThoai_07122024 xeMay) {
                            View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_edit_item, null);
                            EditText edtName = view1.findViewById(R.id.edt_name);
                            EditText edtColor = view1.findViewById(R.id.edt_color);
                            EditText edtPrice = view1.findViewById(R.id.edt_price);
                            EditText edtDescription = view1.findViewById(R.id.edt_description);
                            EditText edtImage = view1.findViewById(R.id.edt_image);
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                                    .setView(view1)
                                    .setCancelable(true);
                            edtName.setText(xeMay.getTenDienThoai());
                            edtColor.setText(xeMay.getNgayNhap());
                            edtPrice.setText(String.valueOf(xeMay.getTrangThai()));
                            edtDescription.setText(xeMay.getMoTa());
                            edtImage.setText(xeMay.getHinhAnh());
                            builder.setPositiveButton("OK", (dialog, which) -> {
                                String newName = edtName.getText().toString();
                                String newDescription = edtDescription.getText().toString();
                                String newPrice = edtPrice.getText().toString();
                                if (!newName.isEmpty() && !newDescription.isEmpty() && !newPrice.isEmpty()) {
                                    long newPriceLong = Long.parseLong(newPrice);
                                    xeMay.setTenDienThoai(newName);
                                    xeMay.setNgayNhap(newDescription);
                                    xeMay.setTrangThai(newPriceLong);
                                    Call<List<DienThoai_07122024>> callUpdatePet = apiService.updateXeMay(xeMay);
                                    callUpdatePet.enqueue(new Callback<List<DienThoai_07122024>>() {
                                        @SuppressLint("NotifyDataSetChanged")
                                        @Override
                                        public void onResponse(Call<List<DienThoai_07122024>> call, Response<List<DienThoai_07122024>> response) {
                                            if (response.isSuccessful()) {
                                                xeMayList.clear();
                                                xeMayList.addAll(response.body());
                                                xeMayAdapter.notifyDataSetChanged();
                                                Toast.makeText(getActivity(), "Cập nhật xe máy thành công", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(getActivity(), "Lỗi khi xe máy thú cưng", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<List<DienThoai_07122024>> call, Throwable throwable) {
                                            Log.e("Update", "onFailure: " + throwable.getMessage());
                                        }
                                    });
                                } else {
                                    Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                                }
                            });
                            builder.setNegativeButton("Cancel", (dialog, which) -> {
                                dialog.dismiss();
                            });
                            Dialog dialog = builder.create();
                            dialog.show();
                        }


                        @Override
                        public void onDeleteClick(DienThoai_07122024 pet) {
                            String id = pet.get_id();
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                                    .setTitle("Xóa thú cưng?")
                                    .setMessage("Bạn có chắc chắn muốn xóa thú cưng này?")
                                    .setPositiveButton("OK", (dialog, which) -> {
                                        Call<List<DienThoai_07122024>> callDeletePet = apiService.deleteXeMay(id);
                                        callDeletePet.enqueue(new Callback<List<DienThoai_07122024>>() {
                                            @SuppressLint("NotifyDataSetChanged")
                                            @Override
                                            public void onResponse(Call<List<DienThoai_07122024>> call, Response<List<DienThoai_07122024>> response) {
                                                xeMayList.clear();
                                                xeMayList.addAll(response.body());
                                                xeMayAdapter.notifyDataSetChanged();
                                                Toast.makeText(getActivity(), "Xóa xe máy thành công", Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onFailure(Call<List<DienThoai_07122024>> call, Throwable throwable) {
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
            public void onFailure(Call<List<DienThoai_07122024>> call, Throwable throwable) {
                Log.e("GetXeMay", "onFailure: " + throwable.getMessage());
            }
        });

        return view;
    }
}
