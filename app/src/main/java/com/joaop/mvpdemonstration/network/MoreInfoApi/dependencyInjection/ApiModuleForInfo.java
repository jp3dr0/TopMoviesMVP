package com.joaop.mvpdemonstration.network.MoreInfoApi.dependencyInjection;

import com.joaop.mvpdemonstration.dependencyInjection.scopes.AppScope;
import com.joaop.mvpdemonstration.network.MoreInfoApi.MoreInfoApi;
import com.joaop.mvpdemonstration.network.MovieApi.MovieApi;
import com.joaop.mvpdemonstration.root.dependencyInjection.ContextModule;

import java.io.IOException;

import dagger.Module;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

// modulo da api
@Module(includes = ContextModule.class)
public class ApiModuleForInfo {

    // url da api
    private final String BASE_URL = "https://omdbapi.com/";

    // key da api
    private final String API_KEY = "9646db38";

    // prover cliente http, para interceptações (debug)
    @Provides
    //@AppScope
    public OkHttpClient provideClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC); // nao queremos ver o corpo da requisição
        // interceptor.setLevel(HttpLoggingInterceptor.Level.BODY; // queremos ver o corpo da requisição
        // interceptor.setLevel(HttpLoggingInterceptor.Level.BODY); // queremos ver apenas o header da requisição
        return new OkHttpClient.Builder().addInterceptor(interceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        // aqui vamos adicionar um interceptador para adicionar a api_key a todas as requisições
                        Request request = chain.request();
                        HttpUrl url = request.url().newBuilder().addQueryParameter("apikey", API_KEY).build();
                        request = request.newBuilder().url(url).build();
                        return chain.proceed(request);
                    }
                })
                .build();
    }

    // prover instancia do retrofit
    @Provides
    //@AppScope
    public Retrofit provideRetrofit(String baseURL, OkHttpClient client){
        return new Retrofit.Builder()
                .baseUrl(baseURL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // adicionando adapter para o rxjava
                .build();
    }

    // prover uma implementação concreta da interface ApiService. perceba que esse metodo usa os outros dois acima
    @Provides
    //@AppScope
    public MoreInfoApi provideMovieApi(){
        return provideRetrofit(BASE_URL, provideClient()).create(MoreInfoApi.class);
    }
}