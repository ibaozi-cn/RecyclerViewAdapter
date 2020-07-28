package com.julive.adapter.core

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

typealias  ViewModelType = ViewModel<*, *>
typealias  DefaultViewModelType<M> = ViewModel<M, DefaultViewHolder>

typealias BindView = DefaultViewHolder.(payloads: List<Any>) -> Unit
typealias UnBindView = DefaultViewHolder.() -> Unit
typealias InitView <M> = DefaultViewHolder.(viewModel: DefaultViewModelType<M>) -> Unit

@Suppress("UNCHECKED_CAST")
abstract class DefaultViewModel<M> : DefaultViewModelType<M> {

    override var model: M? = null
    private var initView: InitView<M>? = null
    private var bindView: BindView? = null
    private var unBindView: UnBindView? = null

    open fun onCreateViewHolder(f: DefaultViewHolder.(viewModel: DefaultViewModelType<M>) -> Unit) {
        initView = f
    }

    open fun onBindViewHolder(f: DefaultViewHolder.(payloads: List<Any>) -> Unit) {
        bindView = f
    }

    open fun onUnBindViewHolder(f: DefaultViewHolder.() -> Unit) {
        unBindView = f
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
            initView?.invoke(
                this,
                getAdapter<IAdapter<*>>()?.getItem(adapterPosition) as @ParameterName(name = "viewModel") DefaultViewModel<M>
            )
        }
    }

    override fun bindVH(viewHolder: DefaultViewHolder, model: M, payloads: List<Any>) {
        bindView?.invoke(viewHolder, payloads)
    }

    override fun unBindVH(viewHolder: DefaultViewHolder) {
        unBindView?.invoke(viewHolder)
    }
}

open class LayoutViewModel<M>(override val layoutRes: Int) : DefaultViewModel<M>() {
    override fun getHolderItemView(parent: ViewGroup, layoutInflater: LayoutInflater): View {
        return layoutInflater.inflate(layoutRes, parent, false)
    }
}