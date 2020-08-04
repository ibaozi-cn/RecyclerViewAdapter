package com.julive.adapter.core

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

typealias ViewModelType = ViewModel<*, *>

abstract class DefaultViewModel<M> : ViewModel<M, DefaultViewHolder> {
    override var model: M? = null
    private var initView: InitView? = null
    override var isFirstInit: Boolean = true
    open fun onCreateViewHolder(f: InitView) {
        initView = f
    }
    abstract
    fun getHolderItemView(
        parent: ViewGroup,
        layoutInflater: LayoutInflater
    ): View
    open fun getViewHolder(v: View) = DefaultViewHolder(v)
    override fun getViewHolder(
        parent: ViewGroup,
        layoutInflater: LayoutInflater
    ): DefaultViewHolder {
        return getViewHolder(getHolderItemView(parent, layoutInflater)).apply {
            initView?.invoke(this)
        }
    }
}
open class LayoutViewModel<M>(override val layoutRes: Int) : DefaultViewModel<M>() {
    override fun getHolderItemView(parent: ViewGroup, layoutInflater: LayoutInflater): View {
        return layoutInflater.inflate(layoutRes, parent, false)
    }
}