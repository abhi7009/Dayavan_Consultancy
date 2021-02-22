package com.abhishek.dayavanconsultancy;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface API {

    public static final String BASE_URL = "http://bmango.in/";

    @GET("vehicles/apis/example/user/0/tbl_vehicles")
    Call<List<Model>> getmodels();
}
