package com.guidovezzoni.bingeworthyshows.tvshow.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.guidovezzoni.bingeworthyshows.R;
import com.guidovezzoni.bingeworthyshows.common.model.presentationlayer.TvShow;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class TvShowDetailActivity extends AppCompatActivity {
    private static final String ARG_SHOW = "SHOW";

    public static Intent newIntent(Context context, TvShow tvShow) {
        Intent intent = new Intent(context, TvShowDetailActivity.class);
        intent.putExtra(ARG_SHOW, tvShow);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show_detail);
        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Toast.makeText(this, "Fab pressed", Toast.LENGTH_LONG).show());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            final TvShow tvShow = getIntent().getParcelableExtra(ARG_SHOW);
            TvShowFragment fragment = TvShowFragment.newInstance(tvShow);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container_single_panel, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
