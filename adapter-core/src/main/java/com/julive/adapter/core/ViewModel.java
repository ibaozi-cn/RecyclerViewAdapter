package com.julive.adapter.core;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public abstract class ViewModel<M, VH extends RecyclerView.ViewHolder, Adapter> implements ViewHolderFactory<VH> {

    private M model;
    private VH viewHolder;
    private int position;
    private Context context;
    private Adapter adapter;

    public abstract VH getViewHolder(View view);

    public abstract void onBindView(Adapter adapter);

    public void unBingView(RecyclerView.ViewHolder holder) {
    }

    public void attachToWindow(RecyclerView.ViewHolder holder) {
    }

    public void detachFromWindow(RecyclerView.ViewHolder holder) {
    }

    @NotNull
    public VH getViewHolder(@NonNull ViewGroup parent) {
        return getViewHolder(createView(parent.getContext(), parent));
    }

    private View createView(Context context, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(getLayoutRes(), viewGroup, false);
    }

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
