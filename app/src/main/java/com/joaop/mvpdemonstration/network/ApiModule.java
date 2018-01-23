package com.joaop.mvpdemonstration.network;

import com.joaop.mvpdemonstration.dependencyInjection.scopes.AppScope;
import com.joaop.mvpdemonstration.root.dependencyInjection.ContextModule;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

// modulo da api
@Module(includes = ContextModule.class)
public class ApiModule {

    // url da api
    private final String BASE_URL = "https://jsonplaceholder.typicode.com/";

    // prover cliente http, para interceptações (debug)
    @Provides
    @AppScope
    public OkHttpClient provideClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY); // queremos ver o corpo da requisição
        // interceptor.setLevel(HttpLoggingInterceptor.Level.BODY); // queremos ver apenas o header da requisição
        return new OkHttpClient.Builder().addInterceptor(interceptor).build();
    }

    // prover instancia do retrofit
    @Provides
    @AppScope
    public Retrofit provideRetrofit(String baseURL, OkHttpClient client){
        return new Retrofit.Builder()
                .baseUrl(baseURL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // adicionando adapter para o rxjava
                .build();
    }

    // prover uma implementação concreta da interface ApiService. perceba que esse metodo usa os outros dois acima
    /*
    @Provides
    @AppScope
    public Api provideApi(){
        return provideRetrofit(BASE_URL, provideClient()).create(Api.class);
    }
    */
}
