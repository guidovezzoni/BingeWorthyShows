package com.guidovezzoni.bingeworthyshows.common.extracttolibrary;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.fernandocejas.arrow.checks.Preconditions;
import com.guidovezzoni.bingeworthyshows.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

@SuppressWarnings("unused")
@SuppressLint("Registered")
public class VmAppCompatActivity<M, P> extends AppCompatActivity {
    private static final String TAG = VmAppCompatActivity.class.getSimpleName();

    private final CompositeDisposable disposables = new CompositeDisposable();

    private ViewModel<M, P> viewModel;

    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Preconditions.checkNotNull((View) findViewById(R.id.activity_tvshow_main_layout),
                "setContentView() (with a layout containing a R.id.activity_tvshow_main_layout) needs to be called before calling super.onCreate()");
        Preconditions.checkNotNull(viewModel,
                "ViewModel needs to be assigned before calling super.onCreate()");

        progressBar = findViewById(R.id.progress_bar);

        //TODO it looks like there is some additional spinning after the data loading
        swipeRefreshLayout = findViewById(R.id.swipeToRefresh);
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setOnRefreshListener(this::subscribeForData);
        }

        subscribeForLoadingState();
    }

    /**
     * Show the spinner when loading is happening
     *
     * @param loading show the spinner
     * @implNote {@link #swipeRefreshLayout always has priority }
     */
    protected void setLoadingState(boolean loading) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(loading);
        } else if (progressBar != null) {
            progressBar.setVisibility(loading ? View.VISIBLE : View.INVISIBLE);
        }
    }

    protected void subscribeForData() {
        setLoadingState(true);
    }

    protected void subscribeForLoadingState() {
        disposables.add(viewModel.getLoadingIndicatorVisibility()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setLoadingState, this::onSubscriptionError));
    }

    protected void onSubscriptionError(Throwable throwable) {
        Log.e(TAG, "Something went wrong.", throwable);
    }

    protected CompositeDisposable getDisposables() {
        return disposables;
    }

    protected ProgressBar getProgressBar() {
        return progressBar;
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }

    protected ViewModel<M, P> getViewModel() {
        return viewModel;
    }

    protected void setViewModel(ViewModel<M, P> viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void onDestroy() {
        disposables.dispose();
        super.onDestroy();
    }
}
