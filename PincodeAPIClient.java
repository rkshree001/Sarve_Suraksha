package com.example.retailapllication.zipcode;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class PincodeAPIClient {
    private static PincodeAPIClient apiClient;
    private Retrofit retrofit;

    private PincodeAPIClient() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://www.postalpincode.in/api/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

    }

    public static synchronized PincodeAPIClient getapiClient() {
        if (apiClient == null) {
            apiClient = new PincodeAPIClient();
        }


        return apiClient;
    }

    public PincodeAPInterface getapiInterface() {

        return retrofit.create(PincodeAPInterface.class);
    }
}
