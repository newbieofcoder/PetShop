package hoanglv.fpoly.petshop.services;

import java.util.List;

import hoanglv.fpoly.petshop.DTO.Pets;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {
    String DOMAIN = "http://192.168.1.203:3000/";

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

    @GET("api/get_cart")
    Call<List<Pets>> getCart(@Query("email") String email);

    @DELETE("api/delete_from_cart")
    Call<List<Pets>> deleteFromCart(@Query("email") String email, @Query("petId") String petId);

    @POST("api/add_to_cart")
    Call<List<Pets>> addToCart(@Query("email") String email, @Query("petId") String petId);
}
