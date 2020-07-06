package com.julive.adapter.anko;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.julive.adapter.core.DefaultViewHolder;
import com.julive.adapter.core.ViewModel;
import org.jetbrains.anko.AnkoComponent;
import org.jetbrains.anko.AnkoContext;
import org.jetbrains.annotations.NotNull;

public abstract class AnkoItemViewModel<M, AnkoView extends AnkoComponent<ViewGroup>>
        extends ViewModel<M, DefaultViewHolder, AnkoListAdapter> {

    protected AnkoView ankoView = onCreateView();

    public abstract AnkoView onCreateView();

    @NotNull
    @Override
    public DefaultViewHolder getViewHolder(@NonNull ViewGroup parent) {
        return getViewHolder(ankoView.createView(AnkoContext.Companion.create(parent.getContext(),parent,false)));
    }

    @Override
    public DefaultViewHolder getViewHolder(View view) {
        return new DefaultViewHolder(view);
    }

    @Override
    public int getLayoutRes() {
        return 0;
    }

    @Override
    public int getItemViewType() {
        return ankoView.hashCode();
    }

    public void reBindView() {
        getAdapter().set(getPosition(), this);
    }

}
