package com.julive.adapter_demo.core

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.julive.adapter.core.ArrayItemViewModel
import com.julive.adapter.core.ArrayListAdapter
import com.julive.adapter.core.DefaultViewHolder
import com.julive.adapter_demo.ModelTest
import com.julive.adapter_demo.R
import java.util.*

/**
 * ViewModel
 */
class ArrayViewModelTest : ArrayItemViewModel<ModelTest>() {

    override fun getLayoutRes() = R.layout.item_test

    override fun getViewHolder(
        parent: ViewGroup,
        layoutInflater: LayoutInflater
    ): DefaultViewHolder<ModelTest> {
        return object : DefaultViewHolder<ModelTest>(
            layoutInflater.inflate(
                layoutRes,
                parent,
                false
            )
        ) {
            init {
              itemView.setOnClickListener {
                  val item =
                      getAdapter<ArrayListAdapter>()?.getItem(adapterPosition) as ArrayViewModelTest
                  item.model.title = "${Random().nextInt(100)}"
                  getAdapter<ArrayListAdapter>()?.set(adapterPosition, item)
              }
            }

            override fun onBindViewHolder(
                viewHolder: RecyclerView.ViewHolder,
                item: ModelTest,
                payloads: List<Any>
            ) {
                getView<TextView>(R.id.tv_title)?.text = item.title
                getView<TextView>(R.id.tv_subTitle)?.text = item.subTitle
            }

            override fun unBindViewHolder(viewHolder: RecyclerView.ViewHolder) {
            }
        }
    }

}