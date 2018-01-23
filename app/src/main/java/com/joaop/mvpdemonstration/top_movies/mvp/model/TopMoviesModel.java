package com.joaop.mvpdemonstration.top_movies.mvp.model;

import com.joaop.mvpdemonstration.top_movies.mvp.TopMoviesMVP;
import com.joaop.mvpdemonstration.top_movies.mvp.model.models.ViewModel;
import com.joaop.mvpdemonstration.top_movies.repository.Repository;

import rx.Observable;

public class TopMoviesModel implements TopMoviesMVP.Model{

    private Repository repository;

    public TopMoviesModel(Repository repository) {
        this.repository = repository;
    }

    @Override
    public Observable<ViewModel> getViewModelData() {
        // Estamos recebendo duas Observables e combinando elas com o a função zip()
        //return Observable.zip(repository.getResultsFromNetwork(), repository.getCountriesFromNetwork(), (result, s) -> new ViewModel(result.getTitle(), s));
        return Observable.zip(repository.getResultData(), repository.getCountryData(), (result, s) -> new ViewModel(result.getTitle(), s));
    }
}
