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
}
