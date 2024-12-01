package hoanglv.fpoly.petshop.services;

import java.util.ArrayList;

import hoanglv.fpoly.petshop.DTO.District;
import hoanglv.fpoly.petshop.DTO.DistrictRequest;
import hoanglv.fpoly.petshop.DTO.Province;
import hoanglv.fpoly.petshop.DTO.ResponeGHN;
import hoanglv.fpoly.petshop.DTO.Ward;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GHNService {
    String GHN_URL = "https://dev-online-gateway.ghn.vn/";

    @GET("shiip/public-api/master-data/province")
    Call<ResponeGHN<ArrayList<Province>>> getListProvince();

    @POST("shiip/public-api/master-data/district")
    Call<ResponeGHN<ArrayList<District>>> getListDistrict(@Body DistrictRequest districtRequest);

    @GET("shiip/public-api/master-data/ward")
    Call<ResponeGHN<ArrayList<Ward>>> getListWard(@Query("district_id") int district_id);
}
