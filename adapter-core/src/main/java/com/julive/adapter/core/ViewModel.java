package com.julive.adapter.core;

import android.content.Context;

import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;

public abstract class ViewModel<M, VH extends RecyclerView.ViewHolder, Adapter> {

    private M model;
    private VH viewHolder;
    private int position;
    private Context context;
    private Adapter adapter;

    public abstract void onBindView(Adapter adapter);

    public int getItemViewType() {
        return getLayoutRes();
    }

    @LayoutRes
    public abstract int getLayoutRes();

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

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Adapter getAdapter() {
        return adapter;
    }

    public void setAdapter(Adapter adapter) {
        this.adapter = adapter;
    }

}
