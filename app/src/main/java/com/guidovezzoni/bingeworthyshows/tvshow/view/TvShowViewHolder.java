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

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

@SuppressWarnings("WeakerAccess")
public class TvShowViewHolder extends RecyclerView.ViewHolder {
    private static final int CORNER_ROUNDING = 40;
    private static final String AVERAGE_VOTE_FORMAT = "%.01f";
    private static final String AVERAGE_VOTE_MISSING = "-";

    private ImageView showImage;
    private TextView showTitle;
    private TextView showAverageVote;

    public TvShowViewHolder(@NonNull View itemView) {
        super(itemView);

        showImage = itemView.findViewById(R.id.show_image);
        showTitle = itemView.findViewById(R.id.show_title);
        showAverageVote = itemView.findViewById(R.id.show_average_vote);
    }

    public void bind(@NonNull TvShow tvShow) {
        showImage.setImageDrawable(null);
        Glide.with(itemView)
                .load(tvShow.getPoster())
                .apply(new RequestOptions().transforms(new CenterInside(), new RoundedCorners(CORNER_ROUNDING)))
                .apply(RequestOptions.placeholderOf(R.mipmap.ic_launcher))
                .into(showImage);

        showTitle.setText(tvShow.getTitle());
        showAverageVote.setText(getRating(tvShow));
    }

    private String getRating(TvShow tvShow) {
        Float averageVote = tvShow.getAverageVote();
        if (averageVote != null) {
            return String.format(Locale.getDefault(), AVERAGE_VOTE_FORMAT, averageVote);
        } else {
            return AVERAGE_VOTE_MISSING;
        }
    }
}
