package com.joaop.mvpdemonstration.top_movies.repository;

import com.joaop.mvpdemonstration.network.MovieApi.models.Result;

import rx.Observable;

public interface Repository {

    Observable<Result> getResultsFromMemory();

    Observable<Result> getResultsFromNetwork();

    Observable<String> getCountriesFromMemory();

    Observable<String> getCountriesFromNetwork();

    Observable<String> getCountryData();

    Observable<Result> getResultData();

}
