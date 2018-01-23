package com.joaop.mvpdemonstration.network.MovieApi;

import com.joaop.mvpdemonstration.network.MovieApi.models.TopRated;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface MovieApi {

    @GET("top_rated")
    Observable<TopRated> getTopRatedMovies(@Query("page") Integer page);
}
