package com.julive.adapter.anko;

import android.view.ViewGroup;

import com.julive.adapter.core.ViewModel;

import org.jetbrains.anko.AnkoComponent;

public abstract class AnkoItemViewModel<M, AnkoView extends AnkoComponent<ViewGroup>>
        extends ViewModel<M, AnkoViewHolder<AnkoView>, AnkoListAdapter> {

    protected AnkoView ankoView = onCreateView();

    public abstract AnkoView onCreateView();

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
