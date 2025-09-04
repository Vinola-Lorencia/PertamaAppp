package edu.uph.m23si2.pertamaapp.API;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("api/provinces.json")
    Call<ApiResponse> getProvinsi();

    @GET("api/regencies/{province_code}.json")
    Call<ApiResponseKota> getKota(@Path("province_code") String provinceCode);

}
