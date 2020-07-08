package com.julive.adapter.core;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.julive.adapter_core.R;

import java.util.Collections;
import java.util.List;

public abstract class ListAdapter<VM extends ViewModel<?, ?, ?>, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    public DefaultViewHolderFactoryCache<ViewHolderFactory<VH>> defaultViewHolderFactoryCache = new DefaultViewHolderFactoryCache<>();
    public LayoutInflater layoutInflater;

    private void onBindVH(@NonNull VH holder, int position, @NonNull List<Object> payloads) {
        ViewModel item = getItem(position);
        item.setAdapter(this);
        holder.itemView.setTag(R.id.list_adapter, this);
        item.onBindViewHolder(holder, item.getModel(), payloads);
        if (holder instanceof DefaultViewHolder) {
            holder.itemView.setTag(R.id.list_adapter_item, item);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        onBindVH(holder, position, Collections.emptyList());
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position, @NonNull List<Object> payloads) {
        onBindVH(holder, position, payloads);
    }

    protected abstract VM getItem(int position);

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VH defaultViewHolder = defaultViewHolderFactoryCache.get(viewType).getViewHolder(parent, layoutInflater);
        defaultViewHolder.itemView.setTag(R.id.list_adapter, this);
        return defaultViewHolder;
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
        Object obj = holder.itemView.getTag(R.id.list_adapter_item);
        if (obj instanceof ViewModel) {
            ((ViewModel) obj).unBindViewHolder(holder);
            if (holder instanceof DefaultViewHolder) {
                ((DefaultViewHolder) holder).unBindViewHolder(holder);
                holder.itemView.setTag(R.id.list_adapter_item, null);
                holder.itemView.setTag(R.id.list_adapter, null);
            }
        }
        super.onViewRecycled(holder);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        Context context = recyclerView.getContext();
        if (layoutInflater == null && context != null) {
            layoutInflater = LayoutInflater.from(context);
        }
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        layoutInflater = null;
    }
}
