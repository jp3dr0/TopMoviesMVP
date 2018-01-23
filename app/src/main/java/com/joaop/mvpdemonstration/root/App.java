package com.joaop.mvpdemonstration.root;

import android.app.Application;

import com.joaop.mvpdemonstration.network.MoreInfoApi.MoreInfoApi;
import com.joaop.mvpdemonstration.network.MovieApi.dependencyInjection.ApiModuleForName;
import com.joaop.mvpdemonstration.network.MovieApi.MovieApi;
import com.joaop.mvpdemonstration.network.MoreInfoApi.dependencyInjection.ApiModuleForInfo;
import com.joaop.mvpdemonstration.repository.dependencyInjection.RepositoryModule;
import com.joaop.mvpdemonstration.root.dependencyInjection.AppComponent;
import com.joaop.mvpdemonstration.root.dependencyInjection.ContextModule;
import com.joaop.mvpdemonstration.root.dependencyInjection.DaggerAppComponent;
import com.joaop.mvpdemonstration.top_movies.repository.Repository;

// Aqui você irá iniciar o ContextModule e AppComponent que será utilizado em toda a App
public class App extends Application {

    private AppComponent component;
    //private ApplicationComponent component;
    public MovieApi movieApi;
    public MoreInfoApi moreInfoApi;

    @Override
    public void onCreate() {
        super.onCreate();


        component = DaggerAppComponent.builder()
                .contextModule(new ContextModule(this))
                .apiModuleForInfo(new ApiModuleForInfo())
                .apiModuleForName(new ApiModuleForName())
                .repositoryModule(new RepositoryModule())
                //.topMoviesModule(new TopMoviesModule())
                //.apiModule(new ApiModule())
                .build();

        movieApi = component.getMovieApi();
        moreInfoApi = component.getMoreInfoApi();

        Repository repository = component.getRepository();

        /*
        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .apiModuleForInfo(new ApiModuleForInfo())
                .apiModuleForName(new ApiModuleForName())
                .topMoviesModule(new TopMoviesModule())
                .build();
        */
    }

    // Metodo para prover o AppComponent para toda a App. Elas precisam do component para receber dependencias injetaveis por ele como a API (getApi())
    public AppComponent getComponent() {
        return this.component;
    }
}
