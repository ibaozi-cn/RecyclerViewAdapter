package com.julive.adapter_demo.sorted

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
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
        mSortedListAdapter.into(
            rv_list,
            GridLayoutManager(this, 3)
        )
        rv_list.setItemViewCacheSize(0)
        (0..1000).map {
            mSortedListAdapter.add(SortedItemViewModelTest().apply {
                model = SortedModelTest(it, "标题$it", "副标题$it")
            })
        }
        var index = 100
        btn_left.setText("新增").setOnClickListener {
            // 要想根据uniqueId更新数据，需要调用updateItem方法
            mSortedListAdapter.add(SortedItemViewModelTest().apply {
                model = SortedModelTest(index++, "标题$index", "新增$index")
            })
        }
        btn_middle.setText("删除").setOnClickListener {
            if (mSortedListAdapter.size > 0) {
                val randomInt = Random.nextInt(0, mSortedListAdapter.size)
                mSortedListAdapter.removeAt(randomInt)
            }
        }
        btn_right.setText("替换").setOnClickListener {
            // 根据uniqueId替换 如果sortId不一样就会触发排序
            if (mSortedListAdapter.size > 0) {
                val randomInt = Random.nextInt(0, mSortedListAdapter.size)
                mSortedListAdapter.set(randomInt, mSortedListAdapter.getItem(randomInt).also {
                    it as SortedItemViewModelTest
                    it.model?.subTitle = "替换副标题"
                })
            }
        }
    }
}