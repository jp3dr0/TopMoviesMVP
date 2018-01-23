package com.joaop.mvpdemonstration;

import android.graphics.Movie;

import com.joaop.mvpdemonstration.dependencyInjection.scopes.AppScope;
import com.joaop.mvpdemonstration.network.MoreInfoApi.MoreInfoApi;
import com.joaop.mvpdemonstration.network.MovieApi.MovieApi;
import com.joaop.mvpdemonstration.network.MovieApi.models.Result;
import com.joaop.mvpdemonstration.network.MovieApi.models.TopRated;
import com.joaop.mvpdemonstration.root.App;
import com.joaop.mvpdemonstration.top_movies.mvp.model.models.ViewModel;
import com.joaop.mvpdemonstration.top_movies.repository.Repository;
import com.joaop.mvpdemonstration.top_movies.repository.TopMoviesRepository;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

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
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscription;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.plugins.RxJavaHooks;
import rx.schedulers.Schedulers;

public class RepositoryTests {

    //@Mock
    MovieApi movieApi;

    //@Mock
    MoreInfoApi moreInfoApi;

    Repository repository;



    @Before
    public void setup() {

        // Override RxJava schedulers
        RxJavaHooks.setOnIOScheduler(new Func1<Scheduler, Scheduler>() {
            @Override
            public Scheduler call(Scheduler scheduler) {
                return Schedulers.immediate();
            }
        });

        RxJavaHooks.setOnComputationScheduler(new Func1<Scheduler, Scheduler>() {
            @Override
            public Scheduler call(Scheduler scheduler) {
                return Schedulers.immediate();
            }
        });

        RxJavaHooks.setOnNewThreadScheduler(new Func1<Scheduler, Scheduler>() {
            @Override
            public Scheduler call(Scheduler scheduler) {
                return Schedulers.immediate();
            }
        });

        // Override RxAndroid schedulers
        final RxAndroidPlugins rxAndroidPlugins = RxAndroidPlugins.getInstance();
        rxAndroidPlugins.registerSchedulersHook(new RxAndroidSchedulersHook() {
            @Override
            public Scheduler getMainThreadScheduler() {
                return Schedulers.immediate();
            }
        });

        movieApi = provideRetrofit("https://api.themoviedb.org/3/movie/", provideClient("c05cf1d62cf7f845087360c9ad03bd02",1)).create(MovieApi.class);

        moreInfoApi = provideRetrofit("https://omdbapi.com/", provideClient("9646db38",2)).create(MoreInfoApi.class);

        repository = new TopMoviesRepository(movieApi, moreInfoApi);
    }

    @After
    public void tearDown() throws Exception {
        RxJavaHooks.reset();
    }

    public OkHttpClient provideClient(final String API_KEY, int i) {
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
                        HttpUrl url = request.url().newBuilder().addQueryParameter(i == 1 ? "api_key" : "apikey", API_KEY).build();
                        request = request.newBuilder().url(url).build();
                        return chain.proceed(request);
                    }
                })
                .build();
    }

    public Retrofit provideRetrofit(String baseURL, OkHttpClient client){
        return new Retrofit.Builder()
                .baseUrl(baseURL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // adicionando adapter para o rxjava
                .build();
    }

    @Test
    public void shouldReturnResultsOnNetwork(){
        //when(movieApi.getTopRatedMovies(1)).thenReturn(new Observable<TopRated>());
        List<Result> results = new ArrayList<>();
        Subscription subscription = movieApi.getTopRatedMovies(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .concatMap(new Func1<TopRated, Observable<Result>>() {
                    @Override
                    public Observable<Result> call(TopRated topRated) {
                        return Observable.from(topRated.getResults());
                    }
                })
                .subscribe(new Observer<Result>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("filmes puxados.");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Result result) {
                        System.out.println(result.getTitle());
                    }
                });
    }

    @Test
    public void shouldReturnResults(){
        Subscription subscription = repository.getResultsFromNetwork().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Result>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("results puxados.");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Result result) {
                        System.out.println(result.getTitle());
                    }
                });
    }

    @Test
    public void shouldReturnCountrys(){
        Subscription subscription = repository.getCountriesFromNetwork().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("countrys puxados.");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(String country) {
                        System.out.println(country);
                    }
                });
    }

    @Test
    public void shouldReturnResultsOnNetworksss(){
        //when(movieApi.getTopRatedMovies(1)).thenReturn(new Observable<TopRated>());
        List<Result> results = new ArrayList<>();
        Subscription subscription = Observable.zip(repository.getResultData(), repository.getCountryData(), new Func2<Result, String, ViewModel>() {
            @Override
            public ViewModel call(com.joaop.mvpdemonstration.network.MovieApi.models.Result result, String s) {
                return new ViewModel(result.getTitle(), s);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ViewModel>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("filmes puxados.");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ViewModel viewModel) {
                        System.out.println(viewModel.getName() + " - " + viewModel.getCountry());
                    }
                });
    }

}
