package com.joaop.mvpdemonstration.root.dependencyInjection;

import com.joaop.mvpdemonstration.dependencyInjection.scopes.AppScope;
import com.joaop.mvpdemonstration.network.MoreInfoApi.MoreInfoApi;
import com.joaop.mvpdemonstration.network.MovieApi.dependencyInjection.ApiModuleForName;
import com.joaop.mvpdemonstration.network.MovieApi.MovieApi;
import com.joaop.mvpdemonstration.network.MoreInfoApi.dependencyInjection.ApiModuleForInfo;
import com.joaop.mvpdemonstration.repository.dependencyInjection.RepositoryModule;
import com.joaop.mvpdemonstration.top_movies.dependencyInjection.TopMoviesModule;
import com.joaop.mvpdemonstration.top_movies.repository.Repository;

import dagger.Component;

// Componente que serve de "Bootstrap" para todos os outros. Ou seja, todos os Modulos feitos na App devem ser declarados aqui
// Esse Componente também serve para prover dependencias que são utilizadas em toda a App, como injetar uma API
@AppScope
@Component(modules = {ApiModuleForInfo.class, ApiModuleForName.class, ContextModule.class, TopMoviesModule.class, RepositoryModule.class})
public interface AppComponent {

    //Api getApi();

    MovieApi getMovieApi();

    MoreInfoApi getMoreInfoApi();

    Repository getRepository();

}