package com.julive.adapter_demo.dsl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DefaultItemAnimator
import com.julive.adapter.core.*
import com.julive.adapter_demo.*
import kotlinx.android.synthetic.main.activity_adapter_dsl.*

class AdapterDslActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = "ListAdapter DSL"
        setContentView(R.layout.activity_adapter_dsl)
        listAdapter {
            addAll(createViewModelList(100))
            addAll(createAnkoViewModelList(100))
            addAll(createBindingViewModelList(100))
            // 绑定 RecyclerView
            into(rv_list_dsl)
            rv_list_dsl.layoutAnimation
        }
    }
}