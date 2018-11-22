package com.guidovezzoni.bingeworthyshows.tvshow.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guidovezzoni.bingeworthyshows.R;
import com.guidovezzoni.bingeworthyshows.common.model.presentationlayer.TvShow;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowViewHolder> {
    private final List<TvShow> internalList;

    public TvShowAdapter() {
        internalList = new ArrayList<>();
    }

    @NonNull
    @Override
    public TvShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tvshow, parent, false);
        return new TvShowViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowViewHolder holder, int position) {
        holder.bind(internalList.get(position));
    }

    @Override
    public int getItemCount() {
        return internalList.size();
    }

    public void updateList(List<TvShow> newList) {
        if (newList == null) {
            return;
        }
        if (!internalList.isEmpty()) {
            internalList.clear();
        }
        if (!newList.isEmpty()) {
            internalList.addAll(newList);
        }
        notifyDataSetChanged();
    }
}
