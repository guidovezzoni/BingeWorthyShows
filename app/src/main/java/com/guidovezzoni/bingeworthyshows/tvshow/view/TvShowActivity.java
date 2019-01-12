package com.guidovezzoni.bingeworthyshows.tvshow.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.guidovezzoni.bingeworthyshows.MainApplication;
import com.guidovezzoni.bingeworthyshows.R;
import com.guidovezzoni.bingeworthyshows.common.di.ViewModelFactory;
import com.guidovezzoni.bingeworthyshows.common.extracttolibrary.VmAppCompatActivity;
import com.guidovezzoni.bingeworthyshows.common.model.presentationlayer.TvShow;
import com.guidovezzoni.bingeworthyshows.common.utils.OnPaginatedScrollListener;
import com.guidovezzoni.bingeworthyshows.tvshow.TvShowViewModel;

import org.reactivestreams.Publisher;

import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;

public class TvShowActivity extends VmAppCompatActivity<List<TvShow>, Integer> {
    // TODO Not sure this is the best implementation, need to review this
    private PublishProcessor<Object> paginator = PublishProcessor.create();
    private Disposable paginatorDisposable;

    private SwipeRefreshLayout swipeRefreshLayout;

    private TvShowAdapter tvShowAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewModelFactory viewModelFactory = ((MainApplication) getApplication()).getDiManager().getViewModelFactory();
        setViewModel(ViewModelProviders.of(this, viewModelFactory).get(TvShowViewModel.class));

        swipeRefreshLayout = findViewById(R.id.swipToRefresh);
        swipeRefreshLayout.setOnRefreshListener(this::subscribeForData);

        RecyclerView mainRecyclerView = findViewById(R.id.list);

        tvShowAdapter = new TvShowAdapter();
        mainRecyclerView.setAdapter(tvShowAdapter);
        mainRecyclerView.addOnScrollListener(new OnPaginatedScrollListener(mainRecyclerView,
                () -> paginator.onNext(new Object())));

        subscribeForData();
        subscribeForLoadingState();
    }

    @Override
    protected void subscribeForLoadingState() {
        getDisposables().add(getViewModel().getLoadingIndicatorVisibility()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(swipeRefreshLayout::setRefreshing, this::onSubscriptionError));
    }

    private void subscribeForData() {
        swipeRefreshLayout.setRefreshing(true);

        if (paginatorDisposable != null) paginatorDisposable.dispose();

        ((TvShowViewModel) getViewModel()).resetPagination();
        tvShowAdapter.clearList();

        paginatorDisposable = paginator
                .onBackpressureDrop()
                .concatMap((Function<Object, Publisher<List<TvShow>>>) this::retrieveListPage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tvShowAdapter::addItemsToList, this::onSubscriptionError);

        getDisposables().add(paginatorDisposable);
        paginator.onNext(new Object());
    }

    private Publisher<List<TvShow>> retrieveListPage(Object object) {
        return ((TvShowViewModel) getViewModel()).get()
                .subscribeOn(Schedulers.io())
                .doFinally(() -> swipeRefreshLayout.setRefreshing(false))
                .toFlowable();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tvshow, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_refresh) {
            subscribeForData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
