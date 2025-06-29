package ru.rococo.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import ru.rococo.model.UserJson;

public interface UserdataApi {

    @GET("api/user")
    Call<UserJson> user(@Header("Authorization") String bearerToken);
}
