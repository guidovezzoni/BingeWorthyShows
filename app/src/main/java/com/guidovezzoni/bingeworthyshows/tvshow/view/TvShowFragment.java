package com.guidovezzoni.bingeworthyshows.tvshow.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.guidovezzoni.bingeworthyshows.R;
import com.guidovezzoni.bingeworthyshows.common.model.presentationlayer.TvShow;
import com.guidovezzoni.bingeworthyshows.common.utils.TvShowUtilsKt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TvShowFragment extends Fragment {
    private static final String ARG_SHOW = "SHOW";

    private TvShow tvShow;

    public static TvShowFragment newInstance(TvShow tvShow) {
        Bundle arguments = new Bundle();
        arguments.putParcelable(ARG_SHOW, tvShow);
        TvShowFragment fragment = new TvShowFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(ARG_SHOW)) {
            tvShow = getArguments().getParcelable(ARG_SHOW);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_show_detail, container, false);

        Activity activity = this.getActivity();
        if (activity != null) {
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);

            if (appBarLayout != null) {
                appBarLayout.setTitle(tvShow.getName());
            }
        }

        final ImageView imageView = rootView.findViewById(R.id.show_detail_image);
        Glide.with(getActivity())
                .load(tvShow.getPosterPath())
                .apply(new RequestOptions().transforms(new CenterCrop()))
                .apply(RequestOptions.placeholderOf(R.mipmap.ic_launcher))
                .into(imageView);

        ((TextView) rootView.findViewById(R.id.show_detail_name)).
                setText(tvShow.getName());

        ((TextView) rootView.findViewById(R.id.show_detail_original_name)).
                setText(tvShow.getOriginalName());

        ((TextView) rootView.findViewById(R.id.show_average_vote)).
                setText(TvShowUtilsKt.getRating(tvShow));

        ((TextView) rootView.findViewById(R.id.show_detail_description)).
                setText(tvShow.getOverview());

        return rootView;
    }
}
