package com.stallar.vms.RestAPI;

import com.stallar.vms.model.User;

import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by lucie on 3/2/2018.
 */

public interface LoginService {
    @POST("api/visits")
    Call<User> basicLogin();
}
