package com.julive.adapter_demo.core

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.julive.adapter.core.ArrayListAdapter
import com.julive.adapter.core.bindListAdapter
import com.julive.adapter_demo.R
import com.julive.adapter_demo.anko.ModelTest
import kotlinx.android.synthetic.main.activity_array_list.*
import kotlinx.android.synthetic.main.include_button_bottom.*
import org.jetbrains.anko.toast
import kotlin.random.Random

/**
 * Activity
 */
class ArrayListActivity : AppCompatActivity() {

    private val mArrayListAdapter by lazy {
        ArrayListAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_array_list)
        rv_list.bindListAdapter(mArrayListAdapter)


        // 新增一个
        new_add.setText("新增").setOnClickListener {
            mArrayListAdapter.add(ArrayViewModelTest().apply {
                model = ModelTest("标题", "副标题")
            })
        }


        // 删除第一个
        delete.setText("删除").setOnClickListener {
            if (mArrayListAdapter.size > 0)
                mArrayListAdapter.removeAt(0)
            else
                toast("请添加新用例后再试")
        }


        // 随机更新
        var updateSize = 0
        update.setText("更新").setOnClickListener {
            updateSize++
            if (mArrayListAdapter.size > 0) {
                val randomInt = Random.nextInt(0, mArrayListAdapter.size)
                mArrayListAdapter.set(randomInt, ArrayViewModelTest().apply {
                    model = ModelTest("标题$updateSize", "副标题$updateSize")
                })
            } else {
                toast("请添加新用例后再试")
            }

        }

    }
}