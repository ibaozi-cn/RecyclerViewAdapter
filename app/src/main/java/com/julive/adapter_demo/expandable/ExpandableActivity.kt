package com.julive.adapter_demo.expandable

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.view.isVisible
import com.julive.adapter.animators.firstAnimation
import com.julive.adapter.core.*
import com.julive.adapter.expandable.*
import com.julive.adapter_demo.R
import com.julive.adapter_demo.sorted.ModelTest
import kotlinx.android.synthetic.main.activity_expandable.*
import kotlinx.android.synthetic.main.include_button_bottom.*

class ExpandableActivity : AppCompatActivity() {

    private val mListAdapter =
        listAdapter {
            (0..100).forEach { _ ->
                add(
                    layoutViewModelDsl(
                        R.layout.item_test_2,
                        ModelTest("title", "subTitle")
                    ) {
                        onBindViewHolder {
                            firstAnimation()
                            val model = getModel<ModelTest>()
                            getView<TextView>(R.id.tv_title).text = model?.title
                            getView<TextView>(R.id.tv_subTitle).text = model?.subTitle
                            getView<TextView>(R.id.tv_subTitle).isVisible = isExpanded(adapterPosition)
                        }
                        itemView.setOnClickListener {
                            toggleExpand(adapterPosition)
                        }
                    })
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = "ListAdapter"
        setContentView(R.layout.activity_expandable)
        mListAdapter.into(rv_list_expandable)
        btn_left.setText("切换单开").setOnClickListener {
            if (!mListAdapter.isMultiExpand) {
                btn_left.setText("切换单开")
            } else {
                btn_left.setText("切换多开")
            }
            mListAdapter.setMultiExpandable(!mListAdapter.isMultiExpand)
        }
        btn_middle.isVisible = false
        btn_right.isVisible = false
    }

    override fun onDestroy() {
        super.onDestroy()
        mListAdapter.onDestroy()
    }
}