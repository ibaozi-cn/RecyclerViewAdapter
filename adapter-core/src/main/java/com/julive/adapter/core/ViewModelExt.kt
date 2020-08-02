package com.julive.adapter.core

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

typealias ViewModelType = ViewModel<*, *>
typealias DefaultViewModelType<M> = ViewModel<M, DefaultViewHolder>


@Suppress("UNCHECKED_CAST")
abstract class DefaultViewModel<M> : DefaultViewModelType<M> {

    override var model: M? = null
    private var initView: InitView? = null

    open fun onCreateViewHolder(f: DefaultViewHolder.() -> Unit) {
        initView = f as InitView
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
            initView?.invoke(this)
        }
    }

}

open class LayoutViewModel<M>(override val layoutRes: Int) : DefaultViewModel<M>() {
    override fun getHolderItemView(parent: ViewGroup, layoutInflater: LayoutInflater): View {
        return layoutInflater.inflate(layoutRes, parent, false)
    }
}