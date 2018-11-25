package com.guidovezzoni.bingeworthyshows.tvshow.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.guidovezzoni.bingeworthyshows.MainApplication;
import com.guidovezzoni.bingeworthyshows.R;
import com.guidovezzoni.bingeworthyshows.common.di.ViewModelFactory;
import com.guidovezzoni.bingeworthyshows.common.model.presentationlayer.TvShow;
import com.guidovezzoni.bingeworthyshows.tvshow.TvShowViewModel;

import org.reactivestreams.Publisher;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;

public class TvShowActivity extends AppCompatActivity {
    private static final String TAG = TvShowActivity.class.getSimpleName();

    private ProgressBar progressBar;


    private final CompositeDisposable disposables = new CompositeDisposable();
    private TvShowViewModel tvShowViewModel;

    private TvShowAdapter tvShowAdapter;


    private PublishProcessor<Integer> paginator = PublishProcessor.create();
    private boolean loading = false;
    private int pageNumber = 1;
    private static final int VISIBLE_THRESHOLD = 1;
    private int lastVisibleItem;
    private int totalItemCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ViewModelFactory viewModelFactory = ((MainApplication) getApplication()).getDiManager().getViewModelFactory();
        tvShowViewModel = ViewModelProviders.of(this, viewModelFactory).get(TvShowViewModel.class);

        progressBar = findViewById(R.id.progress_bar);
        RecyclerView mainRecyclerView = findViewById(R.id.list);
        GridLayoutManager layoutManager = (GridLayoutManager) mainRecyclerView.getLayoutManager();

        tvShowAdapter = new TvShowAdapter();
        mainRecyclerView.setAdapter(tvShowAdapter);
        mainRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = layoutManager.getItemCount();
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                if (!loading && totalItemCount <= (lastVisibleItem + VISIBLE_THRESHOLD)) {
                    pageNumber++;
                    paginator.onNext(pageNumber);
                    loading = true;
                }
            }
        });

        subscribeForData();
    }

    private void subscribeForData() {
        Disposable disposable = paginator
                .onBackpressureDrop()
                .concatMap((Function<Integer, Publisher<List<TvShow>>>) this::apply)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onNext, this::onError);

        disposables.add(disposable);
        paginator.onNext(pageNumber);
    }

    private Publisher<List<TvShow>>  apply(Integer page) {
        loading = true;
        progressBar.setVisibility(View.VISIBLE);
        return tvShowViewModel.get(page)
                .subscribeOn(Schedulers.io())
                .toFlowable();
    }

    private void onNext(List<TvShow> items) {
        tvShowAdapter.addItems(items);
        tvShowAdapter.notifyDataSetChanged();
        loading = false;
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void onError(Throwable throwable) {
        Log.e(TAG, "Network call failed.", throwable);
    }
}
