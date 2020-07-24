package com.julive.adapter.core

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

typealias  ViewModelType = ViewModel<*, *>
typealias  DefaultViewModelType<M> = ViewModel<M, DefaultViewHolder>

typealias BindView = (DefaultViewHolder) -> Unit
typealias BindViewPayload<M> = (DefaultViewHolder, M) -> Unit
typealias ItemClick <M> = (viewModel: DefaultViewModelType<M>, viewHolder: DefaultViewHolder) -> Unit

@Suppress("UNCHECKED_CAST")
abstract class DefaultViewModel<M> : DefaultViewModelType<M> {

    override var adapter: IAdapter<ViewModelType>? = null
    override var model: M? = null
    private var bindView: BindView? = null
    private var bindViewPayload: BindViewPayload<M>? = null
    private var itemClick: ItemClick<M>? = null

    open fun onBindViewHolder(f: (DefaultViewHolder) -> Unit) {
        bindView = f
    }

    open fun onBindViewHolder(f: (DefaultViewHolder, M) -> Unit) {
        bindViewPayload = f
    }

    open fun onItemClick(f: (viewModel: DefaultViewModelType<M>, viewHolder: DefaultViewHolder) -> Unit) {
        itemClick = f
    }

    abstract
    fun getHolderItemView(
        parent: ViewGroup,
        layoutInflater: LayoutInflater
    ): View

    override fun getViewHolder(
        parent: ViewGroup,
        layoutInflater: LayoutInflater
    ): DefaultViewHolder {
        return DefaultViewHolder(getHolderItemView(parent, layoutInflater)).apply {
            itemView.setOnClickListener {
                itemClick?.invoke(
                    adapter?.getItem(adapterPosition) as @ParameterName(name = "viewModel") DefaultViewModel<M>,
                    this
                )
            }
        }
    }

    override fun bindVH(viewHolder: DefaultViewHolder, model: M, payloads: List<Any>) {
        if (payloads.isNotEmpty()) {
            this.model = payloads[0] as M
            bindViewPayload?.invoke(viewHolder, this.model!!)
        } else {
            bindView?.invoke(viewHolder)
        }
    }

    override fun unBindVH(viewHolder: DefaultViewHolder) {}
}

open class LayoutViewModel<M>(override val layoutRes: Int) : DefaultViewModel<M>() {
    override fun getHolderItemView(parent: ViewGroup, layoutInflater: LayoutInflater): View {
        return layoutInflater.inflate(layoutRes, parent, false)
    }
}