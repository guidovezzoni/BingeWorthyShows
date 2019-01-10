package com.guidovezzoni.bingeworthyshows.tvshow.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
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

        final TvShow tvShow = getIntent().getParcelableExtra(ARG_SHOW);
        if (savedInstanceState == null) {
            TvShowFragment fragment = TvShowFragment.newInstance(tvShow);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container_single_panel, fragment)
                    .commit();
        }

        final ImageView imageView = findViewById(R.id.hero_image);
        Glide.with(this)
                .load(tvShow.getBackDropPath())
                .apply(new RequestOptions().transforms(new CenterCrop()))
                .apply(RequestOptions.placeholderOf(R.mipmap.ic_launcher))
                .into(imageView);
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
