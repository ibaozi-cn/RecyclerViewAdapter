package com.julive.adapter_demo.selectable

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import com.julive.adapter.anko.ankoViewModelDsl
import com.julive.adapter.binding.BR
import com.julive.adapter.binding.bindingViewModelDsl
import com.julive.adapter.core.into
import com.julive.adapter.core.layoutViewModelDsl
import com.julive.adapter.core.listAdapter
import com.julive.adapter.selectable.isSelected
import com.julive.adapter.selectable.toggleSelection
import com.julive.adapter_demo.R
import com.julive.adapter_demo.sorted.ModelTest
import kotlinx.android.synthetic.main.activity_selectable.*

class SelectableActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selectable)
        listAdapter {
            (0..10).forEach { _ ->
                add(layoutViewModelDsl<ModelTest>(R.layout.item_test) {
                    model = ModelTest("title", "subTitle")
                    onBindViewHolder {
                        getView<TextView>(R.id.tv_title)?.text = model?.title
                        getView<TextView>(R.id.tv_subTitle)?.text = model?.subTitle
                        val isSelect = isSelected(adapterPosition)
                        if (isSelect) {
                            itemView.setBackgroundResource(R.color.cardview_dark_background)
                        } else {
                            itemView.setBackgroundResource(R.color.cardview_light_background)
                        }
                    }
                    onCreateViewHolder {
                        itemView.setOnClickListener {
                            toggleSelection(adapterPosition)
                        }
                    }
                })
            }
            into(rv_list_selectable)
        }
    }
}