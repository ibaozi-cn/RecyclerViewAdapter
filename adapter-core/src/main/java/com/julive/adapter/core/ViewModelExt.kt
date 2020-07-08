package com.julive.adapter.core

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView

abstract class ArrayItemViewModel<M> : ViewModel<M, DefaultViewHolder<M>, ArrayListAdapter>() {

    override fun unBindViewHolder(viewHolder: DefaultViewHolder<M>) {

    }

    override fun onBindViewHolder(
        viewHolder: DefaultViewHolder<M>,
        model: M,
        payloads: MutableList<Any>
    ) {

    }
}


class ArrayItemViewModelDsl<M> : ArrayItemViewModel<M>() {

    var bindView: ((DefaultViewHolder<M>) -> Unit?)? = null
    var itemClick: ((viewModel: ArrayItemViewModel<M>, viewHolder: DefaultViewHolder<M>) -> Unit?)? =
        null

    fun onBindViewHolder(f: (DefaultViewHolder<M>) -> Unit) {
        bindView = f
    }

    fun onItemClick(f: (viewModel: ArrayItemViewModel<M>, viewHolder: DefaultViewHolder<M>) -> Unit) {
        itemClick = f
    }

    @IdRes
    var layoutId: Int = 0

    override fun onBindViewHolder(
        viewHolder: DefaultViewHolder<M>,
        model: M,
        payloads: MutableList<Any>
    ) {
        bindView?.invoke(viewHolder)
    }

    override fun getViewHolder(
        parent: ViewGroup,
        layoutInflater: LayoutInflater
    ): DefaultViewHolder<M> {
        return object : DefaultViewHolder<M>(layoutInflater.inflate(layoutRes, parent, false)) {
            init {
                itemView.setOnClickListener {
                    itemClick?.invoke(
                        adapter.getItem(adapterPosition) as @ParameterName(name = "viewModel") ArrayItemViewModel<M>,
                        this
                    )
                }
            }
        }
    }

    override fun getLayoutRes(): Int {
        return layoutId
    }

}
