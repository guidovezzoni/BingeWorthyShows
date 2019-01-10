package com.guidovezzoni.bingeworthyshows.tvshow.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.guidovezzoni.bingeworthyshows.R;
import com.guidovezzoni.bingeworthyshows.common.model.presentationlayer.TvShow;
import com.guidovezzoni.bingeworthyshows.common.utils.TvShowUtilsKt;
import com.guidovezzoni.gutils.functional.Action1;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

@SuppressWarnings("WeakerAccess")
public class TvShowViewHolder extends RecyclerView.ViewHolder {
    private static final int CORNER_ROUNDING = 40;

    private ImageView showImage;
    private TextView showTitle;
    private TextView showAverageVote;

    public TvShowViewHolder(@NonNull View itemView) {
        super(itemView);

        showImage = itemView.findViewById(R.id.show_image);
        showTitle = itemView.findViewById(R.id.show_title);
        showAverageVote = itemView.findViewById(R.id.show_average_vote);
    }

    public void bind(@NonNull TvShow tvShow, Action1<TvShow> itemClick) {
        showImage.setImageDrawable(null);
        Glide.with(itemView)
                .load(tvShow.getPosterPath())
                .apply(new RequestOptions().transforms(new CenterInside(), new RoundedCorners(CORNER_ROUNDING)))
                .apply(RequestOptions.placeholderOf(R.mipmap.ic_launcher))
                .into(showImage);

        showTitle.setText(tvShow.getName());
        showAverageVote.setText(TvShowUtilsKt.getRating(tvShow));

        itemView.setOnClickListener(view -> itemClick.run(tvShow));
    }
}
