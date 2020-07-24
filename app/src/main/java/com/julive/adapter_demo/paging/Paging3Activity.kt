package com.julive.adapter_demo.paging

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import com.julive.adapter.core.bindListAdapter
import com.julive.adapter.paging.PagingListAdapter
import com.julive.adapter.paging.PagingLoadStateAdapter
import com.julive.adapter_demo.R
import kotlinx.android.synthetic.main.activity_paging3.*
import kotlinx.android.synthetic.main.include_button_bottom.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class Paging3Activity : AppCompatActivity() {

    private val pagingListAdapter by lazy {
        PagingListAdapter()
    }

    private val viewModel by lazy {
        ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(this.application)).get(
            PagingViewModel::class.java
        )
    }

    @ExperimentalCoroutinesApi
    @ExperimentalPagingApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paging3)

        rv_paging_list.bindListAdapter(pagingListAdapter.withLoadStateFooter(PagingLoadStateAdapter()))

        lifecycleScope.launch {
            viewModel.pager.collect {
                pagingListAdapter.submitData(it)
            }
        }

        pagingListAdapter.addLoadStateListener { loadStates ->
            update.isVisible = loadStates.append is LoadState.Error
            refresh_paging_layout.isRefreshing = loadStates.refresh is LoadState.Loading
        }

        new_add.setText("新增")
        delete.setText("删除")
        update.setText("更新").setOnClickListener {
                pagingListAdapter.refresh()
        }

    }
}