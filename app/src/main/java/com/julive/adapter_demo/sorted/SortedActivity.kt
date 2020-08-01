package com.julive.adapter_demo.sorted

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.julive.adapter.core.into
import com.julive.adapter.sorted.SortedListAdapter
import com.julive.adapter_demo.R
import kotlinx.android.synthetic.main.activity_array_list.*
import kotlinx.android.synthetic.main.include_button_bottom.*
import kotlin.random.Random

class SortedActivity : AppCompatActivity() {

    private val mSortedListAdapter by lazy {
        SortedListAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = "SortedListAdapter"
        setContentView(R.layout.activity_array_list)
        mSortedListAdapter.into(rv_list)
        (0..10).map {
            mSortedListAdapter.add(SortedItemViewModelTest)
        }
        var index = 100
        new_add.setText("新增").setOnClickListener {
            // 要想根据uniqueId更新数据，需要调用updateItem方法
            mSortedListAdapter.add(SortedItemViewModelTest)
        }
        delete.setText("删除").setOnClickListener {
            if (mSortedListAdapter.size > 0) {
                val randomInt = Random.nextInt(0, mSortedListAdapter.size)
                mSortedListAdapter.removeAt(randomInt)
            }
        }
        update.setText("替换").setOnClickListener {
            // 根据uniqueId替换 如果sortId不一样就会触发排序
            if (mSortedListAdapter.size > 0) {
                val randomInt = Random.nextInt(0, mSortedListAdapter.size)
                mSortedListAdapter.set(randomInt, mSortedListAdapter.getItem(randomInt))
            }
        }
    }
}