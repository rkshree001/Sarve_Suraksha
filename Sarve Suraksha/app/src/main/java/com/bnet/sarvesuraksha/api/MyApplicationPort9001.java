package com.bnet.sarvesuraksha.api;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.chuckerteam.chucker.api.ChuckerInterceptor;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MyApplicationPort9001 extends Application {
    final String TAG = getClass().getSimpleName();
    private static MyApplicationPort9001 mInstance;
    private static Retrofit retrofit = null;

    private static final String BASE_URL = "http://178.162.1.133:9001/api/v1/";

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static Retrofit getRetrofitClient(Context context) {
        if (retrofit == null) {
            okhttp3.OkHttpClient client = new okhttp3.OkHttpClient.Builder()
                    .addInterceptor(new ChuckerInterceptor(context))
                    .build();

            retrofit = new Retrofit.Builder()
                    .client(client)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build();
        }
        return retrofit;
    }

    public static synchronized MyApplicationPort9001 getInstance() {
        return mInstance;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }
}
