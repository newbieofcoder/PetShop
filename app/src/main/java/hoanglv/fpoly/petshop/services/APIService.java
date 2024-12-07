package hoanglv.fpoly.petshop.services;

import java.util.List;

import hoanglv.fpoly.petshop.DTO.DienThoai_07122024;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIService {
    String DOMAIN = "http://10.24.4.243:3000/";

    @GET("api/")
    Call<List<DienThoai_07122024>> getXeMay();

    @POST("api/add_dien_thoai")
    Call<List<DienThoai_07122024>> addXeMay(@Body DienThoai_07122024 xeMay);

    @PUT("api/update_dien_thoai")
    Call<List<DienThoai_07122024>> updateXeMay(@Body DienThoai_07122024 xeMay);

    @DELETE("api/delete_dien_thoai/{id}")
    Call<List<DienThoai_07122024>> deleteXeMay(@Path("id") String id);

    @GET("api/search_dien_thoai/{ten_dien_thoai}")
    Call<List<DienThoai_07122024>> searchXeMay(@Path("ten_dien_thoai") String ten_xe);

}
