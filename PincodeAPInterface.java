package com.example.retailapllication.zipcode;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PincodeAPInterface {

    @GET("pincode/{pincode}")
    Call<PincodeResponse> getPincodeDetails(@Path("pincode") String pincode);

}
