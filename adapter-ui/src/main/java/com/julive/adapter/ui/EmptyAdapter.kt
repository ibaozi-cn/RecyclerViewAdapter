package com.julive.adapter.ui

import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.julive.adapter.core.*
import com.julive.adapter.ui.common.WrapAdapter

class DefaultEmptyViewModel : LayoutViewModel<EmptyState>(R.layout.item_empty_layout) {

    init {
        model = EmptyState.NotLoading
    }

    override fun bindVH(viewHolder: DefaultViewHolder, payloads: List<Any>) {
        val text = viewHolder.getView<TextView>(R.id.item_empty_text) as TextView
        val progress = viewHolder.getView<ProgressBar>(R.id.item_empty_progress) as ProgressBar
        when (model) {
            is EmptyState.NotLoading -> {
                text.visibility = View.VISIBLE
                progress.visibility = View.INVISIBLE
                text.text = "数据为空，点我加载"
            }
            is EmptyState.Loading -> {
                text.visibility = View.INVISIBLE
                progress.visibility = View.VISIBLE
                text.text = "加载中"
            }
            is EmptyState.Loaded -> {
                text.visibility = View.VISIBLE
                progress.visibility = View.INVISIBLE
                text.text = "加载成功"
            }
            is EmptyState.Error -> {
                model as EmptyState.Error
                text.visibility = View.VISIBLE
                progress.visibility = View.INVISIBLE
                text.text = "加载失败，点我重试"
            }
        }
    }
}

open class EmptyAdapter(
    mWrappedAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>,
    private val viewModel: DefaultViewModelType<EmptyState> = DefaultEmptyViewModel()
) : WrapAdapter<RecyclerView.ViewHolder>(mWrappedAdapter) {

    var emptyState: EmptyState = EmptyState.NotLoading
        set(value) {
            if (field != value) {
                notifyDataSetChanged()
            }
            field = value
            viewModel.model = value
        }

    override fun getItemCount(): Int {
        return if (displayEmptyView(emptyState)) {
            1
        } else {
            super.getItemCount()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (displayEmptyView(emptyState)) {
            viewModel.itemViewType
        } else {
            super.getItemViewType(position)
        }
    }

    override fun getItemId(position: Int): Long {
        return if (displayEmptyView(emptyState)) {
            super.getItemId(position * 2)
        } else {
            super.getItemId(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (displayEmptyView(emptyState)) {
            viewModel.getViewHolder(parent, LayoutInflater.from(parent.context)).apply {
                itemView.setOnClickListener {
                    if (emptyState is EmptyState.NotLoading || emptyState is EmptyState.Error) {
                        emptyState = EmptyState.Loading
                        Handler().postDelayed({
                            emptyState = EmptyState.Loaded
                        }, 2000)
                    }
                }
            }
        } else {
            super.onCreateViewHolder(parent, viewType)
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: List<Any>
    ) {
        if (displayEmptyView(emptyState)) {
            viewModel.bindVH(holder as DefaultViewHolder, payloads)
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    open fun displayEmptyView(emptyState: EmptyState): Boolean {
        return emptyState is EmptyState.Loading || emptyState is EmptyState.Error || emptyState is EmptyState.NotLoading
    }

}

sealed class EmptyState {
    object NotLoading : EmptyState()
    object Loading : EmptyState()
    object Loaded : EmptyState()
    class Error(val error: Throwable? = null) : EmptyState()
}