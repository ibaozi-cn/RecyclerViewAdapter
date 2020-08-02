package com.julive.adapter_demo.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.julive.adapter_demo.R
import com.julive.adapter_demo.anko.AnkoLayoutActivity
import com.julive.adapter_demo.binding.DataBindingActivity
import com.julive.adapter_demo.core.ArrayListActivity
import com.julive.adapter_demo.diff.DiffActivity
import com.julive.adapter_demo.dsl.AdapterDslActivity
import com.julive.adapter_demo.empty.EmptyActivity
import com.julive.adapter_demo.expandable.ExpandableActivity
import com.julive.adapter_demo.ext.startActivity
import com.julive.adapter_demo.paging.Paging3Activity
import com.julive.adapter_demo.selectable.SelectableActivity
import com.julive.adapter_demo.sorted.SortedActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        btn_anko.setText("AnkoLayoutActivity").setOnClickListener {
            startActivity<AnkoLayoutActivity>()
        }

        btn_array.setText("ArrayListActivity").setOnClickListener {
            startActivity<ArrayListActivity>()
        }

        btn_sorted.setText("SortedActivity").setOnClickListener {
            startActivity<SortedActivity>()
        }

        btn_paging.setText("Paging3Activity").setOnClickListener {
            startActivity<Paging3Activity>()
        }

        btn_dsl.setText("AdapterDslActivity").setOnClickListener {
            startActivity<AdapterDslActivity>()
        }

        btn_diff.setText("DiffActivity").setOnClickListener {
            startActivity<DiffActivity>()
        }

        btn_binding.setText("DataBindingActivity").setOnClickListener {
            startActivity<DataBindingActivity>()
        }

        btn_selectable.setText("SelectableActivity").setOnClickListener {
            startActivity<SelectableActivity>()
        }

        btn_expandable.setText("ExpandableActivity").setOnClickListener {
            startActivity<ExpandableActivity>()
        }

        btn_empty.setText("EmptyActivity").setOnClickListener {
            startActivity<EmptyActivity>()
        }

    }

}