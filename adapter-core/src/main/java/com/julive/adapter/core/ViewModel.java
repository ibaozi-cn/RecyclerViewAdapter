package com.julive.adapter.core;

import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class ViewModel<M, VH extends RecyclerView.ViewHolder, Adapter> implements ViewHolderFactory<VH> {

    private M model;
    private VH viewHolder;
    private int position;
    private Adapter adapter;

    public int getItemViewType() {
        return getLayoutRes();
    }

    @LayoutRes
    public abstract int getLayoutRes();

    public abstract void onBindViewHolder(RecyclerView.ViewHolder viewHolder, M model, List<Object> payloads);

    public abstract void unBindViewHolder(RecyclerView.ViewHolder viewHolder);

    public M getModel() {
        return model;
    }

    public void setModel(M model) {
        this.model = model;
    }

    public VH getViewHolder() {
        return viewHolder;
    }

    public void setViewHolder(VH viewHolder) {
        this.viewHolder = viewHolder;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Adapter getAdapter() {
        return adapter;
    }

    public void setAdapter(Adapter adapter) {
        this.adapter = adapter;
    }

}
