package com.joaop.mvpdemonstration.top_movies.repository;

import com.joaop.mvpdemonstration.network.MoreInfoApi.MoreInfoApi;
import com.joaop.mvpdemonstration.network.MoreInfoApi.models.OmdbApi;
import com.joaop.mvpdemonstration.network.MovieApi.MovieApi;
import com.joaop.mvpdemonstration.network.MovieApi.models.Result;
import com.joaop.mvpdemonstration.network.MovieApi.models.TopRated;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public class TopMoviesRepository implements Repository {

    private MovieApi movieApi;
    private MoreInfoApi moreInfoApi;
    private List<String> countries;
    private List<Result> results;
    private long timestamp; // tempo em q foi feita a ultima atualização

    private static final long STALE_MS = 60 * 1000; // os dados locais sao considerados desatualizados apos 20 segundos

    public TopMoviesRepository(MovieApi movieApi, MoreInfoApi moreInfoApi) {
        this.movieApi = movieApi;
        this.moreInfoApi = moreInfoApi;
        this.timestamp = System.currentTimeMillis();
        countries = new ArrayList<>();
        results = new ArrayList<>();
    }

    public boolean isUpToDate() {
        long tempo_atual = System.currentTimeMillis();
        // timestamp = tempo em que foi feita a ultima atualização do repositorio
        // estamos checando se a diferença entre o tempo atual e o tempo que foi feita a ultima atualização é menor que 20s
        // ou seja, se faz 20s que nao foi feita nenhuuma atualização dos dados no repositorio
        long tempo_limite = STALE_MS;
        long ultima_atualizacao = timestamp;
        long diferenca = tempo_atual - ultima_atualizacao;
        boolean is;
        is = diferenca < tempo_limite;
        return is;
    }

    @Override
    public Observable<Result> getResultsFromMemory() {
        boolean atualizado = isUpToDate();
        if (atualizado) { // se nao faz mais de 20s q nao atualizamos o repositorio
            return Observable.from(results); // entao vamos retornar os dados salvos no repositorio, pois nao estao tao desatualizados

        }
        else { // se faz mais de 20s q nao atualizamos o repositorio, entao os dados estao desatualizados
            timestamp = System.currentTimeMillis(); // atualizamos o timestamp
            results.clear(); // limpamos a memoria atual
            return Observable.empty(); // vamos retornar um observable vazio, já que existe logica para caso isso retorne empty, seja feita uma requisição a api (getResultsFromNetwork())
        }
    }

    @Override
    public Observable<Result> getResultsFromNetwork() {
        // cada pagina retorna 20 filmes, e estamos concatenando 3 paginas. entao estamos esperando 60 filmes
        Observable<TopRated> topRatedObservable = movieApi.getTopRatedMovies(1).concatWith(movieApi.getTopRatedMovies(2)).concatWith(movieApi.getTopRatedMovies(3));
        // concatMap pra pegar apenas os resultados da observable de forma ordenada (flatMap nao preseva a ordem dos elementos)
        Observable<Result> data = Observable.empty();
        data = topRatedObservable.concatMap(new Func1<TopRated, Observable<Result>>() {
            @Override
            public Observable<Result> call(TopRated topRated) {
                return Observable.from(topRated.getResults());
            }
        }).doOnNext(new Action1<Result>() {
            @Override
            public void call(Result result) {
                results.add(result); // adicionando cada result a lista local
            }
        });
        return data;
    }

    @Override
    public Observable<String> getCountriesFromMemory() {
        boolean atualizado = isUpToDate();
        if (atualizado) {
            return Observable.from(countries);
        }
        else {
            timestamp = System.currentTimeMillis();
            countries.clear();
            return Observable.empty();
        }
    }

    @Override
    public Observable<String> getCountriesFromNetwork() {
        // primeiro pegamos a lista de filmes usando o getResultsFromNetwork para pesquisar os resultados nessa segunda api com mais informações
        Observable<String> data = Observable.empty();
        data = getResultsFromNetwork().concatMap(new Func1<Result, Observable<OmdbApi>>() {
            @Override
            public Observable<OmdbApi> call(Result result) {
                return moreInfoApi.getCountry(result.getTitle()); // pegando o nome do país do filme da lista de resultados
            }
        }).concatMap(new Func1<OmdbApi, Observable<String>>() {
            @Override
            public Observable<String> call(OmdbApi omdbApi) {
                return Observable.just(omdbApi.getCountry()); // retornando apenas o nome do país
            }
        }).doOnNext(new Action1<String>() {
            @Override
            public void call(String s) {
                countries.add(s); // adicionando a lista local de países
            }
        });
        return data;
    }

    @Override
    public Observable<String> getCountryData() {
        Observable<String> data;
        data = getCountriesFromMemory().switchIfEmpty(getCountriesFromNetwork());
        return data;

        //Observable<String> data = getCountriesFromMemory();
        //if (countries.isEmpty()) {
        //    data = getCountriesFromNetwork();
        //}
        //return data;

    }

    @Override
    public Observable<Result> getResultData() {
        Observable<Result> data;
        data = getResultsFromMemory().switchIfEmpty(getResultsFromNetwork());
        return data;

        //Observable<Result> data = getResultsFromMemory();
        //if(results.isEmpty()){
        //    data = getResultsFromNetwork();
        //}
        //return  data;

    }
}
