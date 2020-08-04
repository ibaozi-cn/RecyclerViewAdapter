package com.julive.adapter_demo.paging

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import com.julive.adapter.animators.intoWithAnimator
import com.julive.adapter.paging.PagingListAdapter
import com.julive.adapter.paging.PagingLoadStateAdapter
import com.julive.adapter_demo.R
import kotlinx.android.synthetic.main.activity_paging3.*
import kotlinx.android.synthetic.main.include_button_bottom.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class Paging3Activity : AppCompatActivity() {

    private val mPagingListAdapter by lazy {
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
        supportActionBar?.title = "PagingListAdapter"
        setContentView(R.layout.activity_paging3)
        mPagingListAdapter.withLoadStateFooter(PagingLoadStateAdapter()).intoWithAnimator(rv_paging_list)
        lifecycleScope.launch {
            viewModel.pager.collect {
                mPagingListAdapter.submitData(it)
            }
        }
        mPagingListAdapter.addLoadStateListener { loadStates ->
            btn_right.isVisible = loadStates.append is LoadState.Error
            refresh_paging_layout.isRefreshing = loadStates.refresh is LoadState.Loading
        }

        btn_left.setText("新增")
        btn_middle.setText("删除")
        btn_right.setText("更新").setOnClickListener {
                mPagingListAdapter.refresh()
        }

    }
}