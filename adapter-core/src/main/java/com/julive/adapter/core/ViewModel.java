package com.julive.adapter.core;

import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class ViewModel<M, VH extends RecyclerView.ViewHolder, Adapter> implements ViewHolderFactory<VH> {

    private M model;
    private Adapter adapter;

    public int getItemViewType() {
        return getLayoutRes();
    }

    @LayoutRes
    public abstract int getLayoutRes();

    public abstract void onBindViewHolder(VH viewHolder, M model, List<Object> payloads);

    public abstract void unBindViewHolder(VH viewHolder);

    public M getModel() {
        return model;
    }

    public void setModel(M model) {
        this.model = model;
    }

    public Adapter getAdapter() {
        return adapter;
    }

    public void setAdapter(Adapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public String toString() {
        return "ViewModel{" +
                "model=" + model +
                ", adapter=" + adapter +
                '}';
    }
}
