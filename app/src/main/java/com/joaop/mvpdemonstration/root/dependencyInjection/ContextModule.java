package com.joaop.mvpdemonstration.root.dependencyInjection;

import android.content.Context;

import com.joaop.mvpdemonstration.dependencyInjection.qualifiers.AppContext;
import com.joaop.mvpdemonstration.dependencyInjection.scopes.AppScope;

import dagger.Module;
import dagger.Provides;

// Modulo para prover contexto para toda a aplicação. Onde for usado contexto para construir alguma dependencia, esse modulo sera utilizado.
@Module
public class ContextModule {

    private final Context context;

    public ContextModule(Context context) {
        this.context = context;
    }

    @Provides
    @AppContext
    @AppScope
    public Context provideContext(){
        return context;
    }

}
