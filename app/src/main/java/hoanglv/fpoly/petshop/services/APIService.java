package hoanglv.fpoly.petshop.services;

import java.util.List;

import hoanglv.fpoly.petshop.DTO.XeMay;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {
    String DOMAIN = "http://192.168.1.204:3000/";

    @GET("api/xe_may_list")
    Call<List<XeMay>> getXeMay();

    @POST("api/add_xe_may")
    Call<List<XeMay>> addXeMay(@Body XeMay xeMay);

    @PUT("api/update_xe_may")
    Call<List<XeMay>> updateXeMay(@Body XeMay xeMay);

    @DELETE("api/delete_xe_may/{id}")
    Call<List<XeMay>> deleteXeMay(@Path("id") String id);

    @GET("api/search_xe_may")
    Call<List<XeMay>> searchXeMay(@Query("key") String key);

}
