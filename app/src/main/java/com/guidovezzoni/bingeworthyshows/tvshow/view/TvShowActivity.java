package com.guidovezzoni.bingeworthyshows.tvshow.view;

import android.os.Bundle;

import com.guidovezzoni.bingeworthyshows.MainApplication;
import com.guidovezzoni.bingeworthyshows.R;
import com.guidovezzoni.bingeworthyshows.common.extracttolibrary.VmAppCompatActivity;
import com.guidovezzoni.bingeworthyshows.common.di.ViewModelFactory;
import com.guidovezzoni.bingeworthyshows.common.model.presentationlayer.TvShow;
import com.guidovezzoni.bingeworthyshows.common.utils.OnPaginatedScrollListener;
import com.guidovezzoni.bingeworthyshows.tvshow.TvShowViewModel;

import org.reactivestreams.Publisher;

import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;

public class TvShowActivity extends VmAppCompatActivity<List<TvShow>, Integer> {
    // TODO Not sure this is the best implementation, need to review this
    private PublishProcessor<Object> paginator = PublishProcessor.create();

    private TvShowAdapter tvShowAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewModelFactory viewModelFactory = ((MainApplication) getApplication()).getDiManager().getViewModelFactory();
        setViewModel(ViewModelProviders.of(this, viewModelFactory).get(TvShowViewModel.class));

        RecyclerView mainRecyclerView = findViewById(R.id.list);

        tvShowAdapter = new TvShowAdapter();
        mainRecyclerView.setAdapter(tvShowAdapter);
        mainRecyclerView.addOnScrollListener(new OnPaginatedScrollListener(mainRecyclerView,
                () -> paginator.onNext(new Object())));

        subscribeForData();
    }

    private void subscribeForData() {
        Disposable disposable = paginator
                .onBackpressureDrop()
                .concatMap((Function<Object, Publisher<List<TvShow>>>) this::apply)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onNext, this::onSubscriptionError);

        getDisposables().add(disposable);
        paginator.onNext(new Object());
    }

    private Publisher<List<TvShow>> apply(Object object) {
        return getViewModel().get(null)
                .subscribeOn(Schedulers.io())
                .toFlowable();
    }

    private void onNext(List<TvShow> items) {
        tvShowAdapter.addItems(items);
        tvShowAdapter.notifyDataSetChanged();
    }
}
