package edu.uph.m23si2.pertamaapp.API;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("api/provinces.json")
    Call<ApiResponse> getProvinsi();
}
