package com.julive.adapter_demo.diff

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.julive.adapter.core.into
import com.julive.adapter.core.listAdapter
import com.julive.adapter.diff.calculateDiff
import com.julive.adapter_demo.R
import com.julive.adapter_demo.createViewModelList
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
            val list = createViewModelList(3, "Diff更新")
            adapter.calculateDiff(list)
        }
    }
}



