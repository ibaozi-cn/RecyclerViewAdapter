package com.julive.adapter.core;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class ListAdapter<VM extends ViewModel<?, ?, ?>, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> implements List<VM> {

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        ViewModel item = getItem(position);
        item.setViewHolder(holder);
        item.setPosition(position);
        item.setContext(holder.itemView.getContext());
        item.setAdapter(this);
        item.onBindView(this);
    }

    abstract VM getItem(int position);

}
