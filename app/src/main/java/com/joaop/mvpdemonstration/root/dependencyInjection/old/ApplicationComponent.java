package com.joaop.mvpdemonstration.root.dependencyInjection.old;

import com.joaop.mvpdemonstration.network.MoreInfoApi.dependencyInjection.ApiModuleForInfo;
import com.joaop.mvpdemonstration.network.MovieApi.dependencyInjection.ApiModuleForName;
import com.joaop.mvpdemonstration.top_movies.dependencyInjection.TopMoviesModule;
import com.joaop.mvpdemonstration.top_movies.mvp.view.TopMoviesActivity;

import javax.inject.Singleton;

import dagger.Component;

//@Singleton
//@Component(modules = {ApplicationModule.class, ApiModuleForName.class, ApiModuleForInfo.class, TopMoviesModule.class})
public interface ApplicationComponent {
    void inject(TopMoviesActivity target);
}
