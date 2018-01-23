package com.joaop.mvpdemonstration.top_movies.mvp;

import com.joaop.mvpdemonstration.top_movies.mvp.model.models.ViewModel;

import rx.Observable;

public interface TopMoviesMVP {

    interface View {
        // Instruir o Recycler View que existe outro objeto a ser adicionado
        void updateData(ViewModel viewModel);

        // Mostrar mensagens ao usuario (String s)
        void showSnackBar(String s);
    }

    interface Presenter {
        void loadData();

        void rxUnsubscribe();

        void setView(View view);
    }

    interface Model {
        Observable<ViewModel> getViewModelData();
    }
}


