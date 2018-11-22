package com.guidovezzoni.bingeworthyshows.tvshow.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.guidovezzoni.bingeworthyshows.R;
import com.guidovezzoni.bingeworthyshows.common.model.presentationlayer.TvShow;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

@SuppressWarnings("WeakerAccess")
public class TvShowViewHolder extends RecyclerView.ViewHolder {
    private ImageView image;
    private TextView origin;

    public TvShowViewHolder(@NonNull View itemView) {
        super(itemView);

        image = itemView.findViewById(R.id.image);
        origin = itemView.findViewById(R.id.title);
    }

    public void bind(@NonNull TvShow tvShow) {

        origin.setText(tvShow.getTitle());
    }
}
