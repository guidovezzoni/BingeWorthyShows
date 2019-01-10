package com.guidovezzoni.bingeworthyshows.tvshow.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;

public class TvShowActivity extends AppCompatActivity {
    private static final String TAG = TvShowActivity.class.getSimpleName();

    private final CompositeDisposable disposables = new CompositeDisposable();
    private TvShowViewModel tvShowViewModel;

    // TODO Not sure this is the best implementation, need to review this
    private PublishProcessor<Object> paginator = PublishProcessor.create();
    private Disposable paginatorDisposable;

    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;

    private TvShowAdapter tvShowAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewModelFactory viewModelFactory = ((MainApplication) getApplication()).getDiManager().getViewModelFactory();
        tvShowViewModel = ViewModelProviders.of(this, viewModelFactory).get(TvShowViewModel.class);

        swipeRefreshLayout = findViewById(R.id.swipToRefresh);
        swipeRefreshLayout.setOnRefreshListener(this::subscribeForData);


        progressBar = findViewById(R.id.progress_bar);
        RecyclerView mainRecyclerView = findViewById(R.id.list);

        tvShowAdapter = new TvShowAdapter();
        mainRecyclerView.setAdapter(tvShowAdapter);
        mainRecyclerView.addOnScrollListener(new OnPaginatedScrollListener(mainRecyclerView,
                () -> paginator.onNext(new Object())));

        subscribeForLoadingState();
        subscribeForData();
    }

    private void subscribeForLoadingState() {
        disposables.add(tvShowViewModel.getLoadingIndicatorVisibility()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(swipeRefreshLayout::setRefreshing, this::onError));
    }

    private void subscribeForData() {
        swipeRefreshLayout.setRefreshing(true);

        if (paginatorDisposable != null) paginatorDisposable.dispose();

        tvShowViewModel.resetPagination();
        tvShowAdapter.clearList();

        paginatorDisposable = paginator
                .onBackpressureDrop()
                .concatMap((Function<Object, Publisher<List<TvShow>>>) this::retrieveListPage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tvShowAdapter::addItemsToList, this::onError);

        disposables.add(paginatorDisposable);
        paginator.onNext(new Object());
    }

    private Publisher<List<TvShow>> retrieveListPage(Object object) {
        return tvShowViewModel.get()
                .subscribeOn(Schedulers.io())
                .doFinally(() -> swipeRefreshLayout.setRefreshing(false))
                .toFlowable();
    }

    @SuppressLint("LogNotTimber")
    private void onError(Throwable throwable) {
        Log.e(TAG, "Something went wrong.", throwable);
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
