package com.julive.adapter_demo.empty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.julive.adapter.core.into
import com.julive.adapter.core.listAdapter
import com.julive.adapter.ui.EmptyAdapter
import com.julive.adapter.ui.EmptyState
import com.julive.adapter_demo.R
import com.julive.adapter_demo.createViewModelList
import com.julive.adapter_demo.main.setBtnText
import kotlinx.android.synthetic.main.activity_empty.*
import kotlinx.android.synthetic.main.include_button_bottom.*

class EmptyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empty)
        val emptyAdapter = EmptyAdapter(
            listAdapter {
                addAll(createViewModelList())
            }
        ).apply {
            into(rv_list_empty)
        }
        btn_left.setBtnText("空布局").setOnClickListener {
            emptyAdapter.emptyState = EmptyState.NotLoading
        }
        btn_middle.setBtnText("加载中").setOnClickListener {
            emptyAdapter.emptyState = EmptyState.Loading
            Handler().postDelayed({
                emptyAdapter.emptyState = EmptyState.Loaded
            },2000)
        }
        btn_right.setBtnText("加载失败").setOnClickListener {
            emptyAdapter.emptyState = EmptyState.Error
        }
    }
}