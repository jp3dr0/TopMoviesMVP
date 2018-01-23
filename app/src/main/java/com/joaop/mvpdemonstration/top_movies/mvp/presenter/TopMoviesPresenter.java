package com.joaop.mvpdemonstration.top_movies.mvp.presenter;

import com.joaop.mvpdemonstration.top_movies.mvp.TopMoviesMVP;
import com.joaop.mvpdemonstration.top_movies.mvp.model.models.ViewModel;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TopMoviesPresenter implements TopMoviesMVP.Presenter{

    private TopMoviesMVP.View view;
    private Subscription subscription = null; // Declarando a Inscrição do RXJava globalmente mantem ela viva
    private TopMoviesMVP.Model model;

    public TopMoviesPresenter(TopMoviesMVP.Model model) {
        this.model = model;
    }

    @Override
    public void loadData() {
        // Essa inscrição vai se manter viva, puxando novos dados de tempo em tempo (note que nada é feito no onCompleted(). Ela so vai parar de puxar dados chamando o rxUnsubscribe()
        subscription = model.getViewModelData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ViewModel>() {
                    @Override
                    public void onCompleted() {
                        view.showSnackBar("A lista de filmes foi puxada com sucesso");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if (view != null){
                            view.showSnackBar("Erro ao procurar filmes");
                        }
                    }

                    @Override
                    public void onNext(ViewModel viewModel) {
                        if(view != null){
                            view.updateData(viewModel);
                        }
                    }
                });
    }

    // Metodo pra desligar a inscrição, pra nao ficar gastando dados sem razao
    @Override
    public void rxUnsubscribe() {
        if (subscription != null) { // se estiver inscrito
            if (!subscription.isUnsubscribed()) { // se a inscrição nao ja estiver desligada
                subscription.unsubscribe(); // desligar a inscrição, desinscrever
            }
        }
    }

    @Override
    public void setView(TopMoviesMVP.View view) {
        this.view = view;
    }
}
