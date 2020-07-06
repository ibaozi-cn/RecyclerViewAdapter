package com.julive.adapter.core;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class ListAdapter<VM extends ViewModel<?, ?, ?>, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    public DefaultViewHolderFactoryCache<ViewHolderFactory<VH>> defaultViewHolderFactoryCache = new DefaultViewHolderFactoryCache<>();
    public RecyclerView recyclerView;

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        ViewModel item = getItem(position);
        item.setViewHolder(holder);
        item.setPosition(position);
        item.setContext(holder.itemView.getContext());
        item.setAdapter(this);
        item.onBindView(this);
    }

    protected abstract VM getItem(int position);

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return defaultViewHolderFactoryCache.get(viewType).getViewHolder(parent);
    }

    @Override
    public int getItemViewType(int position) {
        ViewModel item = getItem(position);
        if (item == null) {
            return super.getItemViewType(position);
        }
        int type = item.getItemViewType();
        if (!defaultViewHolderFactoryCache.contains(type)) {
            defaultViewHolderFactoryCache.register(type, item);
        }
        return type;
    }

    @Override
    public void onViewRecycled(@NonNull VH holder) {
        if (holder.getAdapterPosition() > -1)
            getItem(holder.getAdapterPosition()).unBingView(holder);
        super.onViewRecycled(holder);
    }

    @Override
    public void onViewAttachedToWindow(@NonNull VH holder) {
        if (holder.getAdapterPosition() > -1)
            getItem(holder.getAdapterPosition()).attachToWindow(holder);
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull VH holder) {
        if (holder.getAdapterPosition() > -1)
            getItem(holder.getAdapterPosition()).detachFromWindow(holder);
        super.onViewDetachedFromWindow(holder);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView rv) {
        defaultViewHolderFactoryCache.clear();
        recyclerView = null;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView rv) {
        recyclerView = rv;
    }
}
