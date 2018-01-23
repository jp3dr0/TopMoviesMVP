package com.joaop.mvpdemonstration.top_movies.dependencyInjection;

import com.joaop.mvpdemonstration.dependencyInjection.qualifiers.TopMoviesContext;
import com.joaop.mvpdemonstration.dependencyInjection.scopes.TopMoviesScope;
import com.joaop.mvpdemonstration.root.dependencyInjection.AppComponent;
import com.joaop.mvpdemonstration.top_movies.mvp.view.TopMoviesActivity;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Provides;

@Component(modules = {TopMoviesModule.class}, dependencies = {AppComponent.class})
@TopMoviesContext
@TopMoviesScope
public interface TopMoviesComponent {
    // Isso vai injetar esse component (incluindo seus modulos e dependencias) em uma TopMoviesActivity
    //@TopMoviesContext
    void injectOnTopMoviesActivity(TopMoviesActivity topMoviesActivity);
}
