package hoanglv.fpoly.petshop.services;

import java.util.ArrayList;
import java.util.List;

import hoanglv.fpoly.petshop.DTO.Bill;
import hoanglv.fpoly.petshop.DTO.BillDetails;
import hoanglv.fpoly.petshop.DTO.District;
import hoanglv.fpoly.petshop.DTO.DistrictRequest;
import hoanglv.fpoly.petshop.DTO.Pets;
import hoanglv.fpoly.petshop.DTO.Province;
import hoanglv.fpoly.petshop.DTO.ResponeGHN;
import hoanglv.fpoly.petshop.DTO.Ward;
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

    @GET("api/pet_list")
    Call<List<Pets>> getPets();

    @POST("api/add_pet")
    Call<List<Pets>> addPet(@Body Pets pets);

    @PUT("api/update_pet")
    Call<List<Pets>> updatePet(@Body Pets pets);

    @DELETE("api/delete_pet/{id}")
    Call<List<Pets>> deletePet(@Path("id") String id);

    @GET("api/search_pet")
    Call<List<Pets>> search(@Query("key") String key);

    @GET("api/cart")
    Call<List<Bill>> getCart();

    @DELETE("api/delete_cart/{id}")
    Call<List<Bill>> deleteFromCart(@Path("id") String id);

    @POST("api/add_to_cart")
    Call<List<Bill>> addToCart(@Body Bill bill);

    @PUT("api/update_cart")
    Call<List<Bill>> updateCart(@Body Bill bill);

    @GET("api/bill_details")
    Call<List<BillDetails>> getBillDetails();

    @DELETE("api/delete_bill_details/{id}")
    Call<List<BillDetails>> deleteBillDetails(@Path("id") String id);

    @POST("api/add_bill_details")
    Call<List<BillDetails>> addBillDetails(@Body BillDetails billDetails);

    @PUT("api/update_bill_details")
    Call<List<BillDetails>> updateBillDetails(@Body Bill bill);
}
