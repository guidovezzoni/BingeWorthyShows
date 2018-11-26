package com.guidovezzoni.bingeworthyshows.common.utils;

import com.fernandocejas.arrow.checks.Preconditions;
import com.guidovezzoni.bingeworthyshows.common.model.functional.Action;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OnPaginatedScrollListener extends RecyclerView.OnScrollListener {
    private static final int VISIBLE_THRESHOLD = 1;

    private final LinearLayoutManager layoutManager;
    private final Action onReachingBottom;
    private boolean loading = false;

    public OnPaginatedScrollListener(@NonNull RecyclerView recyclerView, Action onReachingBottom) {
        this.layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        this.onReachingBottom = onReachingBottom;

        Preconditions.checkNotNull(adapter,"Adapter needs to be not null");

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                loading = false;
            }
        });
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int totalItemCount = layoutManager.getItemCount();
        int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
        if (!loading && totalItemCount <= (lastVisibleItem + VISIBLE_THRESHOLD)) {
            onReachingBottom.run();
            loading = true;
        }
    }
}
