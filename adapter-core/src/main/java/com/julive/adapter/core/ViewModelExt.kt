package com.julive.adapter.core

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

typealias  ViewModelType = ViewModel<*, *>
typealias  DefaultViewModelType<M> = ViewModel<M, DefaultViewHolder<M>>

@Suppress("UNCHECKED_CAST")
abstract class DefaultViewModel<M> : DefaultViewModelType<M>, Subscriber<DefaultViewHolder<M>> {

    private var initView: InitView<M>? = null
    override var model: M? = null

    fun onCreateViewHolder(f: DefaultViewHolder<M>.() -> Unit) {
        initView = f
    }

    abstract
    fun getHolderItemView(
        parent: ViewGroup,
        layoutInflater: LayoutInflater
    ): View

    override fun onBindViewHolder(
        viewHolder: DefaultViewHolder<M>,
        position: Int,
        payloads: List<Any>
    ) {

    }

    override fun unBindViewHolder(viewHolder: DefaultViewHolder<M>, position: Int) {
    }

    override fun getViewHolder(
        parent: ViewGroup,
        layoutInflater: LayoutInflater
    ): DefaultViewHolder<M> {
        return object : DefaultViewHolder<M>(getHolderItemView(parent, layoutInflater)) {
            init {
                initView?.invoke(this)
            }
        }
    }

}

open class LayoutViewModel<M>(override val layoutRes: Int) :
    DefaultViewModel<M>() {
    override fun getHolderItemView(parent: ViewGroup, layoutInflater: LayoutInflater): View {
        return layoutInflater.inflate(layoutRes, parent, false)
    }
}