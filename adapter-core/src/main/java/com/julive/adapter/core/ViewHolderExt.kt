package com.julive.adapter.core

import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import com.julive.adapter_core.R

fun <Adapter : IAdapter<*>> RecyclerView.ViewHolder.getAdapter(): Adapter? {
    return this.itemView.getTag(R.id.adapter) as? Adapter
}

fun <VM : ViewModelType> RecyclerView.ViewHolder.getViewModel(): VM? {
    return this.itemView.getTag(R.id.adapter_item) as? VM
}

fun <M> RecyclerView.ViewHolder.getModel(): M? {
    return (this.itemView.getTag(R.id.adapter_item) as? ViewModelType)?.model as? M
}

fun <T : View> RecyclerView.ViewHolder.getView(@IdRes viewId: Int): T {
    return itemView.findViewById(viewId) as T
}

typealias BindView = DefaultViewHolder.(payloads: List<Any>) -> Unit
typealias UnBindView = DefaultViewHolder.() -> Unit
typealias InitView = DefaultViewHolder.() -> Unit

open class DefaultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), Subscriber {

    private var bindView: BindView? = null
    private var unBindView: UnBindView? = null

    fun onBindViewHolder(f: BindView) {
        bindView = f
    }

    fun onUnBindViewHolder(f: UnBindView) {
        unBindView = f
    }

    override fun onBindViewHolder(
        position: Int,
        payloads: List<Any>
    ) {
        bindView?.invoke(this, payloads)
    }

    override fun unBindViewHolder(position: Int) {
        unBindView?.invoke(this)
    }

}
