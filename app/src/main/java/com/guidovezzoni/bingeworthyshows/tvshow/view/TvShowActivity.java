package com.guidovezzoni.bingeworthyshows.tvshow.view;

import android.os.Bundle;
import android.util.Log;

import com.guidovezzoni.bingeworthyshows.MainApplication;
import com.guidovezzoni.bingeworthyshows.R;
import com.guidovezzoni.bingeworthyshows.common.di.ViewModelFactory;
import com.guidovezzoni.bingeworthyshows.common.model.presentationlayer.TvShow;
import com.guidovezzoni.bingeworthyshows.tvshow.TvShowViewModel;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class TvShowActivity extends AppCompatActivity {
    private static final String TAG = TvShowActivity.class.getSimpleName();

    private final CompositeDisposable disposables = new CompositeDisposable();
    private TvShowViewModel tvShowViewModel;

    private TvShowAdapter tvShowAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        ViewModelFactory viewModelFactory = ((MainApplication) getApplication()).getDiManager().getViewModelFactory();
        tvShowViewModel = ViewModelProviders.of(this, viewModelFactory).get(TvShowViewModel.class);

        RecyclerView vehicleRecyclerView = findViewById(R.id.list);

        tvShowAdapter = new TvShowAdapter();
        vehicleRecyclerView.setAdapter(tvShowAdapter);

        fetchData();
    }

    private void fetchData() {
        disposables.add(tvShowViewModel.get(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateUiRecycler, this::onError));
    }

    private void updateUiRecycler(List<TvShow> list) {
        tvShowAdapter.updateList(list);
        if (list.isEmpty()) {
//            Toast.makeText(this, R.string.main_activity_no_result, Toast.LENGTH_LONG).show();
        }
    }

    private void onError(Throwable throwable) {
        Log.e(TAG, "Network call failed.", throwable);
    }

}
