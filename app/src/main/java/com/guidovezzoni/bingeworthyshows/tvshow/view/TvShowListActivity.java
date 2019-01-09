package com.guidovezzoni.bingeworthyshows.tvshow.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.guidovezzoni.bingeworthyshows.MainApplication;
import com.guidovezzoni.bingeworthyshows.R;
import com.guidovezzoni.bingeworthyshows.common.di.ViewModelFactory;
import com.guidovezzoni.bingeworthyshows.common.model.presentationlayer.TvShow;
import com.guidovezzoni.bingeworthyshows.common.utils.OnPaginatedScrollListener;
import com.guidovezzoni.bingeworthyshows.tvshow.TvShowViewModel;

import org.reactivestreams.Publisher;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;

public class TvShowListActivity extends AppCompatActivity {
    private static final String TAG = TvShowListActivity.class.getSimpleName();

    private final CompositeDisposable disposables = new CompositeDisposable();
    private TvShowViewModel tvShowViewModel;

    // TODO Not sure this is the best implementation, need to review this
    private PublishProcessor<Object> paginator = PublishProcessor.create();

    private ProgressBar progressBar;
    private TvShowAdapter tvShowAdapter;

    private boolean twoPanelLoaded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        twoPanelLoaded = (findViewById(R.id.item_detail_container_two_panel) != null);

        ViewModelFactory viewModelFactory = ((MainApplication) getApplication()).getDiManager().getViewModelFactory();
        tvShowViewModel = ViewModelProviders.of(this, viewModelFactory).get(TvShowViewModel.class);

        progressBar = findViewById(R.id.progress_bar);
        RecyclerView mainRecyclerView = findViewById(R.id.list);

        tvShowAdapter = new TvShowAdapter(this::itemClick);
        mainRecyclerView.setAdapter(tvShowAdapter);
        mainRecyclerView.addOnScrollListener(new OnPaginatedScrollListener(mainRecyclerView,
                () -> paginator.onNext(new Object())));

        subscribeForLoadingState();
        subscribeForData();
    }

    private void itemClick(TvShow tvShow) {
        if (twoPanelLoaded) {
            TvShowFragment fragment = TvShowFragment.newInstance(tvShow);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container_two_panel, fragment)
                    .commit();
        } else {
            this.startActivity(TvShowDetailActivity.newIntent(this, tvShow));
        }
    }

    private void subscribeForLoadingState() {
        disposables.add(tvShowViewModel.getLoadingIndicatorVisibility()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateSpinner, this::onError));
    }

    private void updateSpinner(Boolean loading) {
        progressBar.setVisibility(loading ? View.VISIBLE : View.INVISIBLE);
    }

    private void subscribeForData() {
        Disposable disposable = paginator
                .onBackpressureDrop()
                .concatMap((Function<Object, Publisher<List<TvShow>>>) this::apply)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tvShowAdapter::addItems, this::onError);

        disposables.add(disposable);
        paginator.onNext(new Object());
    }

    private Publisher<List<TvShow>> apply(Object object) {
        return tvShowViewModel.get()
                .subscribeOn(Schedulers.io())
                .toFlowable();
    }

    private void onError(Throwable throwable) {
        Log.e(TAG, "Something went wrong.", throwable);
    }
}
