package com.guidovezzoni.bingeworthyshows.common.baselibrary;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.guidovezzoni.bingeworthyshows.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

@SuppressLint("Registered")
public class VmAppCompatActivity<M, P> extends AppCompatActivity {
    private static final String TAG = VmAppCompatActivity.class.getSimpleName();

    private final CompositeDisposable disposables = new CompositeDisposable();

    private ViewModel<M, P> viewModel;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressBar = findViewById(R.id.progress_bar);

        subscribeForLoadingState();
    }

    protected CompositeDisposable getDisposables() {
        return disposables;
    }

    public ViewModel<M, P> getViewModel() {
        return viewModel;
    }

    public void setViewModel(ViewModel<M, P> viewModel) {
        this.viewModel = viewModel;
    }

    private void subscribeForLoadingState() {
        if (progressBar != null) {
            disposables.add(viewModel.getLoadingIndicatorVisibility()
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::updateProgressBar, this::onError));
        }
    }

    private void updateProgressBar(Boolean loading) {
        if (progressBar != null) {
            progressBar.setVisibility(loading ? View.VISIBLE : View.INVISIBLE);
        }
    }

    protected void onError(Throwable throwable) {
        Log.e(TAG, "Something went wrong.", throwable);
    }

    @Override
    public void onDestroy() {
        disposables.dispose();
        super.onDestroy();
    }
}
