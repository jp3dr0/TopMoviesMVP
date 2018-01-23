package com.joaop.mvpdemonstration.top_movies.dependencyInjection;

import com.joaop.mvpdemonstration.dependencyInjection.qualifiers.AppContext;
import com.joaop.mvpdemonstration.dependencyInjection.qualifiers.TopMoviesContext;
import com.joaop.mvpdemonstration.dependencyInjection.scopes.AppScope;
import com.joaop.mvpdemonstration.dependencyInjection.scopes.RepositoryScope;
import com.joaop.mvpdemonstration.dependencyInjection.scopes.TopMoviesScope;
import com.joaop.mvpdemonstration.network.MoreInfoApi.MoreInfoApi;
import com.joaop.mvpdemonstration.network.MovieApi.MovieApi;
import com.joaop.mvpdemonstration.top_movies.mvp.TopMoviesMVP;
import com.joaop.mvpdemonstration.top_movies.mvp.model.TopMoviesModel;
import com.joaop.mvpdemonstration.top_movies.repository.Repository;
import com.joaop.mvpdemonstration.top_movies.mvp.presenter.TopMoviesPresenter;
import com.joaop.mvpdemonstration.top_movies.mvp.view.TopMoviesActivity;
import com.joaop.mvpdemonstration.top_movies.repository.TopMoviesRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
//@TopMoviesContext
public class TopMoviesModule {

    private final TopMoviesActivity topMoviesActivity;

    public TopMoviesModule(TopMoviesActivity topMoviesActivity) {
        this.topMoviesActivity = topMoviesActivity;
    }

    @Provides
    @TopMoviesScope
    public TopMoviesActivity topMoviesActivity(){
        return this.topMoviesActivity;
    }


    @Provides
    @TopMoviesScope
    public TopMoviesMVP.Presenter provideTopMoviesActivityPresenter(TopMoviesMVP.Model model){
        return new TopMoviesPresenter(model);
    }

    @Provides
    @TopMoviesScope
    public TopMoviesMVP.Model provideTopMoviesActivityModel(Repository repository){
        return new TopMoviesModel(repository);
    }
    /*
    @Provides
    //@RepositoryScope
    //@TopMoviesScope
    //@TopMoviesContext
    @AppScope
    //@Singleton
    public Repository provideTopMoviesActivityRepository(MovieApi movieApi, MoreInfoApi moreInfoApi){
        return new TopMoviesRepository(movieApi, moreInfoApi);
    }
    */
}
