package com.julive.adapter.anko;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;
import com.julive.adapter.core.ListAdapter;
import com.julive.adapter.core.DefaultViewModel;
import com.julive.adapter_anko.R;

import org.jetbrains.anko.AnkoComponent;
import org.jetbrains.anko.AnkoContext;
import org.jetbrains.annotations.NotNull;

public abstract class AnkoViewModel<M, AnkoView extends AnkoComponent<ViewGroup>>
        extends DefaultViewModel<M, ListAdapter> {

    @Override
    public int getLayoutRes() {
        return 0;
    }

    public abstract AnkoView onCreateView();

    @NotNull
    @Override
    public View getHolderItemView(@NotNull ViewGroup parent, @NotNull LayoutInflater layoutInflater) {
        AnkoView ankoView = onCreateView();
        View view = ankoView.createView(AnkoContext.Companion.create(parent.getContext(), parent, false));
        view.setTag(R.id.list_adapter_anko_view, ankoView);
        return view;
    }

    @Override
    public int getItemViewType() {
        return this.hashCode();
    }

    public AnkoView getAnkoView(RecyclerView.ViewHolder viewHolder) {
        return (AnkoView) viewHolder.itemView.getTag(R.id.list_adapter_anko_view);
    }

}
