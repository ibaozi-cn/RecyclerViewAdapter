package com.julive.adapter_demo.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import com.julive.adapter_demo.R
import com.julive.adapter_demo.binding.DataBindingActivity
import com.julive.adapter_demo.core.ArrayListActivity
import com.julive.adapter_demo.diff.DiffActivity
import com.julive.adapter_demo.dsl.AdapterDslActivity
import com.julive.adapter_demo.empty.EmptyActivity
import com.julive.adapter_demo.expandable.ExpandableActivity
import com.julive.adapter_demo.ext.startActivity
import com.julive.adapter_demo.filter.FilterActivity
import com.julive.adapter_demo.paging.Paging3Activity
import com.julive.adapter_demo.selectable.SelectableActivity
import com.julive.adapter_demo.sorted.SortedActivity
import com.julive.adapter_demo.video.VideoListActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        btn_array.setBtnText("ArrayListActivity").setOnClickListener {
            startActivity<ArrayListActivity>()
        }

        btn_sorted.setBtnText("SortedActivity").setOnClickListener {
            startActivity<SortedActivity>()
        }

        btn_paging.setBtnText("Paging3Activity").setOnClickListener {
            startActivity<Paging3Activity>()
        }

        btn_dsl.setBtnText("AdapterDslActivity").setOnClickListener {
            startActivity<AdapterDslActivity>()
        }

        btn_diff.setBtnText("DiffActivity").setOnClickListener {
            startActivity<DiffActivity>()
        }

        btn_binding.setBtnText("DataBindingActivity").setOnClickListener {
            startActivity<DataBindingActivity>()
        }

        btn_selectable.setBtnText("SelectableActivity").setOnClickListener {
            startActivity<SelectableActivity>()
        }

        btn_expandable.setBtnText("ExpandableActivity").setOnClickListener {
            startActivity<ExpandableActivity>()
        }

        btn_empty.setBtnText("EmptyActivity").setOnClickListener {
            startActivity<EmptyActivity>()
        }

        btn_filter.setBtnText("FilterActivity").setOnClickListener {
            startActivity<FilterActivity>()
        }

        btn_video.setBtnText("VideoActivity").setOnClickListener {
            startActivity<VideoListActivity>()
        }

    }

}

fun AppCompatButton.setBtnText(content: String): AppCompatButton {
    text = content
    return this
}