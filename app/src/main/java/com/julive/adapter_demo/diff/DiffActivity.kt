package com.julive.adapter_demo.diff

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.core.view.isVisible
import com.julive.adapter.core.*
import com.julive.adapter.diff.calculateDiff
import com.julive.adapter_demo.R
import com.julive.adapter_demo.createViewModelList
import com.julive.adapter_demo.sorted.ModelTest
import kotlinx.android.synthetic.main.activity_diff.*
import kotlinx.android.synthetic.main.include_button_bottom.*

class DiffActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diff)
        supportActionBar?.title = "ListAdapter Diff"

        val adapter = listAdapter {
            //循环添加ItemViewModel
            addAll(createViewModelList(2))
            // 绑定 RecyclerView
            into(rv_diff_list)
        }
        btn_left.isVisible = false
        btn_middle.isVisible = false
        btn_right.setText("更新").setOnClickListener {
            val list = buildDiffModelList()
            adapter.calculateDiff(list)
        }
    }
}

fun buildDiffModelList() = (0..3).map {
    layoutViewModelDsl(
        if (it % 2 == 0) R.layout.item_test else R.layout.item_test_2,
        ModelTest("title$it", "Diff更新$it")
    ) {
        // 绑定数据
        onBindViewHolder {
            val model = getModel<ModelTest>()
            getView<TextView>(R.id.tv_title).text = model?.title
            getView<TextView>(R.id.tv_subTitle).text = model?.subTitle
        }
    }
}


