package com.julive.adapter_demo.filter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import com.julive.adapter.core.ListAdapter
import com.julive.adapter.core.into
import com.julive.adapter.core.listAdapter
import com.julive.adapter.filter.filter
import com.julive.adapter_demo.R
import com.julive.adapter_demo.createMapDataViewModel
import com.julive.adapter_demo.sorted.ModelTest
import kotlinx.android.synthetic.main.activity_filter.*

class FilterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                recyclerView.filter<ListAdapter>(query) { constraint ->
                    val allData = dataList
                    listAdapter {
                        addAll(allData.filter {
                            (it.model as? ModelTest)?.subTitle?.contains(constraint) ?: false
                        })
                    }
                }
                return !query.isNullOrEmpty()
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query.isNullOrEmpty()) {
                    recyclerView.filter<ListAdapter>(query) {
                        listAdapter { }
                    }
                }
                return !query.isNullOrEmpty()
            }
        })

        listAdapter {
            addAll(createMapDataViewModel())
            into(recyclerView)
        }

    }
}