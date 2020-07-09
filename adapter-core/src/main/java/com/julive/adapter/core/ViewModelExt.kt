package com.julive.adapter.core

import android.view.LayoutInflater
import android.view.ViewGroup
import java.lang.Exception

typealias  DefaultViewModelType <M, Adapter> = ViewModel<M, DefaultViewHolder, Adapter>
typealias  ArrayViewModelType <M> = DefaultItemViewModel<M, ArrayListAdapter>

typealias BindView = (DefaultViewHolder) -> Unit
typealias ItemClick <M> = (viewModel: ArrayItemViewModel<M>, viewHolder: DefaultViewHolder) -> Unit

abstract class DefaultItemViewModel<M, A : IAdapter<*>> : DefaultViewModelType<M, A> {

    override var adapter: A? = null
    override var model: M? = null
    private var bindView: BindView? = null
    private var itemClick: ItemClick<M>? = null

    open fun onBindViewHolder(f: (DefaultViewHolder) -> Unit) {
        bindView = f
    }

    open fun onItemClick(f: (viewModel: ArrayItemViewModel<M>, viewHolder: DefaultViewHolder) -> Unit) {
        itemClick = f
    }

    override fun getViewHolder(
        parent: ViewGroup,
        layoutInflater: LayoutInflater
    ): DefaultViewHolder {
        return DefaultViewHolder(layoutInflater.inflate(layoutRes, parent, false)).apply {
            itemView.setOnClickListener {
                itemClick?.invoke(
                    adapter?.getItem(adapterPosition) as @ParameterName(name = "viewModel") ArrayItemViewModel<M>,
                    this
                )
            }
        }
    }

    override fun bindVH(viewHolder: DefaultViewHolder, model: M, payloads: List<Any>) {
        bindView?.invoke(viewHolder)
    }

    override fun unBindVH(viewHolder: DefaultViewHolder) {}
}

open class ArrayItemViewModel<M>(override val layoutRes: Int) : ArrayViewModelType<M>()