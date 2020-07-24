package com.julive.adapter.binding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.julive.adapter.core.ListAdapter
import com.julive.adapter.core.DefaultViewModel
import com.julive.adapter.core.DefaultViewHolder

open class BindingViewModel<M>(override val layoutRes: Int, private val variableId: Int) :
    DefaultViewModel<M, ListAdapter>() {

    override fun getHolderItemView(parent: ViewGroup, layoutInflater: LayoutInflater): View {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            layoutInflater,
            layoutRes,
            parent,
            false
        )
        val view = binding.root
        view.setTag(R.id.list_adapter_binding, binding)
        return view
    }

    private fun getBinding(viewHolder: RecyclerView.ViewHolder): ViewDataBinding {
        return viewHolder.itemView.getTag(R.id.list_adapter_binding) as ViewDataBinding
    }

    override fun bindVH(viewHolder: DefaultViewHolder, model: M, payloads: List<Any>) {
        super.bindVH(viewHolder, model, payloads)
        getBinding(viewHolder).setVariable(variableId, model)
        getBinding(viewHolder).executePendingBindings()
    }

}