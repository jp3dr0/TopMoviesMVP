package com.joaop.mvpdemonstration.repository.dependencyInjection;

import com.joaop.mvpdemonstration.dependencyInjection.qualifiers.AppContext;
import com.joaop.mvpdemonstration.dependencyInjection.scopes.AppScope;
import com.joaop.mvpdemonstration.network.MoreInfoApi.MoreInfoApi;
import com.joaop.mvpdemonstration.network.MoreInfoApi.dependencyInjection.ApiModuleForInfo;
import com.joaop.mvpdemonstration.network.MovieApi.MovieApi;
import com.joaop.mvpdemonstration.network.MovieApi.dependencyInjection.ApiModuleForName;
import com.joaop.mvpdemonstration.top_movies.repository.Repository;
import com.joaop.mvpdemonstration.top_movies.repository.TopMoviesRepository;

import dagger.Module;
import dagger.Provides;

@Module(includes = {ApiModuleForName.class, ApiModuleForInfo.class})
public class RepositoryModule {
    //private final Repository repository;

    //public RepositoryModule(MovieApi movieApi, MoreInfoApi moreInfoApi) {
    //    this.repository = new TopMoviesRepository(movieApi, moreInfoApi);
    //}

    @Provides
    //@RepositoryScope
    //@TopMoviesScope
    //@TopMoviesContext
    @AppScope
    //@AppContext
    //@Singleton
    public Repository provideTopMoviesActivityRepository(MovieApi movieApi, MoreInfoApi moreInfoApi){
        return new TopMoviesRepository(movieApi, moreInfoApi);
    }
}
