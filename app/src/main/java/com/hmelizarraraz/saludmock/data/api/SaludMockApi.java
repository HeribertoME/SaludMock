package com.hmelizarraraz.saludmock.data.api;

import com.hmelizarraraz.saludmock.data.api.model.LoginBody;
import com.hmelizarraraz.saludmock.data.api.model.Affiliate;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by heriberto on 26/05/17.
 */

public interface SaludMockApi {

    public static final String BASE_URL = "http://192.168.1.11/api.saludmock.com/v1/";

    @POST("affiliates/login")
    Call<Affiliate> login(@Body LoginBody loginBody);
}
