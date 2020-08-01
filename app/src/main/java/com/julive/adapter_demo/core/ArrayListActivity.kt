package com.julive.adapter_demo.core

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.julive.adapter.core.ListAdapter
import com.julive.adapter.core.into
import com.julive.adapter_demo.R
import com.julive.adapter_demo.sorted.ModelTest
import kotlinx.android.synthetic.main.activity_array_list.*
import kotlinx.android.synthetic.main.include_button_bottom.*
import org.jetbrains.anko.toast
import kotlin.random.Random

/**
 * Activity
 */
class ArrayListActivity : AppCompatActivity() {

    private val mArrayListAdapter by lazy {
        ListAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = "ListAdapter"
        setContentView(R.layout.activity_array_list)
        mArrayListAdapter.into(rv_list)
        // 新增一个
        new_add.setText("新增").setOnClickListener {
            mArrayListAdapter.add(createViewModel().apply {
                model = ModelTest("title", "subtitle")
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
                mArrayListAdapter.set(randomInt, createViewModel())
            } else {
                toast("请添加新用例后再试")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.action_ll -> {
                rv_list.layoutManager = LinearLayoutManager(this)
                true
            }
            R.id.action_flex -> {
//                rv_list.layoutManager = flexboxLayoutMangerDefault{}
                true
            }
            R.id.action_staggered -> {
                rv_list.layoutManager = StaggeredGridLayoutManager(
                    2,
                    StaggeredGridLayoutManager.VERTICAL
                )
                true
            }
            R.id.action_grid -> {
                rv_list.layoutManager = GridLayoutManager(this, 2)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}