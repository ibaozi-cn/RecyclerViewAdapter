package com.julive.adapter_demo.binding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.julive.adapter.core.ListAdapter
import com.julive.adapter.core.bindListAdapter
import com.julive.adapter_demo.R
import com.julive.adapter_demo.sorted.ModelTest
import kotlinx.android.synthetic.main.activity_data_binding.*

class DataBindingActivity : AppCompatActivity() {

    private val mArrayListAdapter by lazy {
        ListAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_binding)

        rv_binding_list.bindListAdapter(mArrayListAdapter)

        (0..10).forEach{
            mArrayListAdapter.add(BindingItemViewModelTest().apply {
                model = ModelTest("标题$it","副标题$it")
            })
        }
    }

}