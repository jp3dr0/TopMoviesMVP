package com.joaop.mvpdemonstration.network.MoreInfoApi;

import com.joaop.mvpdemonstration.network.MoreInfoApi.models.OmdbApi;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface MoreInfoApi {
    @GET("/")
    Observable<OmdbApi> getCountry(@Query("t") String title);
}
