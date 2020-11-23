package com.example.toqsoft;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestInterface {

    @GET("employees")
    Call<ResponModel> getEmpoyJson();
}
