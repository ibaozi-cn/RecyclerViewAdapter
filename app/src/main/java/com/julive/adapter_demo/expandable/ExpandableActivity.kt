package com.julive.adapter_demo.expandable

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.view.isVisible
import com.julive.adapter.core.into
import com.julive.adapter.core.layoutViewModelDsl
import com.julive.adapter.core.listAdapter
import com.julive.adapter.expandable.isExpanded
import com.julive.adapter.expandable.isMultiExpand
import com.julive.adapter.expandable.setMultiExpandable
import com.julive.adapter.expandable.toggleExpand
import com.julive.adapter_demo.R
import com.julive.adapter_demo.sorted.ModelTest
import kotlinx.android.synthetic.main.activity_expandable.*
import kotlinx.android.synthetic.main.activity_selectable.*
import kotlinx.android.synthetic.main.include_button_bottom.*

class ExpandableActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expandable)
        val adapter = listAdapter {
            (0..10).forEach { _ ->
                add(layoutViewModelDsl<ModelTest>(R.layout.item_test_2) {
                    model = ModelTest("title", "subTitle")
                    onBindViewHolder {
                        getView<TextView>(R.id.tv_title)?.text = model?.title
                        getView<TextView>(R.id.tv_subTitle)?.text = model?.subTitle
                        getView<TextView>(R.id.tv_subTitle)?.isVisible = isExpanded(adapterPosition)
                    }
                    onCreateViewHolder {
                        itemView.setOnClickListener {
                            toggleExpand(adapterPosition)
                        }
                    }
                })
            }
            into(rv_list_expandable)
        }

        new_add.setText("切换单开").setOnClickListener {
            if(!adapter.isMultiExpand){
                new_add.setText("切换单开")
            }else{
                new_add.setText("切换多开")
            }
            adapter.setMultiExpandable(!adapter.isMultiExpand)
        }

        delete.isVisible = false
        update.isVisible = false

    }
}