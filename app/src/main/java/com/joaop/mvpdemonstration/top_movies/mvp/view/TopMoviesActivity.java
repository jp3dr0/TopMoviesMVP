package com.joaop.mvpdemonstration.top_movies.mvp.view;

import android.graphics.Movie;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;
import android.support.design.widget.Snackbar;

import com.joaop.mvpdemonstration.R;
import com.joaop.mvpdemonstration.root.App;
import com.joaop.mvpdemonstration.top_movies.dependencyInjection.DaggerTopMoviesComponent;
import com.joaop.mvpdemonstration.top_movies.dependencyInjection.TopMoviesComponent;
import com.joaop.mvpdemonstration.top_movies.dependencyInjection.TopMoviesModule;
import com.joaop.mvpdemonstration.top_movies.mvp.TopMoviesMVP;
import com.joaop.mvpdemonstration.top_movies.mvp.model.models.ViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopMoviesActivity extends AppCompatActivity implements TopMoviesMVP.View{

    // Bind das views com Butter Knife
    @BindView(R.id.rv)
    RecyclerView recyclerView;

    @BindView(R.id.rootView)
    ViewGroup rootView;

    // Atributos
    private ListAdapter listAdapter;
    private List<ViewModel> resultList = new ArrayList<>();
    private static final String TAG = "TopMoviesActivity";

    // Injetando dependencias
    @Inject
    TopMoviesMVP.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_movies);

        ButterKnife.bind(this);

        App app = (App) getApplication();


        TopMoviesComponent component = DaggerTopMoviesComponent.builder()
                .appComponent(app.getComponent())
                //.applicationComponent(app.getComponent())
                .topMoviesModule(new TopMoviesModule(this))
                .build();

        component.injectOnTopMoviesActivity(this);


        //app.getComponent().inject(this);

        configurarRecyclerView();

    }

    public void configurarRecyclerView() {
        listAdapter = new ListAdapter(resultList); // Adapter generico para listas do android
        recyclerView.setAdapter(listAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL)); // decoração para dividir os itens
        recyclerView.setItemAnimator(new DefaultItemAnimator()); // animação padrao para os itens
        recyclerView.setHasFixedSize(true); // melhorando o desempenho
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
        presenter.loadData();
        resultList.size();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.rxUnsubscribe();
    }

    @Override
    public void updateData(ViewModel viewModel) {
        resultList.add(viewModel);
        if(resultList.isEmpty()){
            listAdapter.notifyItemInserted(0);
        }
        else{
            listAdapter.notifyItemInserted(resultList.size() - 1);
        }
        Log.d(TAG, "updateData: " + resultList.size());
    }

    @Override
    public void showSnackBar(String s) {
        Snackbar.make(rootView, s, Snackbar.LENGTH_SHORT).show();
    }
}
