package hoanglv.fpoly.petshop.services;

import static hoanglv.fpoly.petshop.services.GHNService.GHN_URL;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GHNRequest {
    public final static String SHOPID = "195528";
    public final static String TokenGHN = "1eea98e0-afa4-11ef-9083-dadc35c0870d";
    private GHNService ghnService;

    public GHNRequest() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @NonNull
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .addHeader("ShopId", SHOPID)
                        .addHeader("Token", TokenGHN)
                        .build();
                return chain.proceed(request);
            }
        });

        ghnService = new Retrofit.Builder()
                .baseUrl(GHN_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build().create(GHNService.class);
    }

    public GHNService callAPI() {
        return ghnService;
    }
}
