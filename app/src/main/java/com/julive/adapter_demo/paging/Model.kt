package com.julive.adapter_demo.paging

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import com.julive.adapter.core.SameModel
import com.julive.adapter.paging.PagingItemViewModel
import com.julive.adapter_demo.R
import kotlinx.coroutines.delay

class PagingModelTest(val title: String, override var uniqueId: String = title) : SameModel

class PagingViewModel : ViewModel() {

    val pager by lazy {
        Pager(
            config = PagingConfig(pageSize = 3, prefetchDistance = 1),
            pagingSourceFactory = { MainSource() }).flow
    }

    class MainSource : PagingSource<Int, PagingItemViewModel<*>>() {
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PagingItemViewModel<*>> {
            // 如果key是null，那就加载第0页的数据
            val page = params.key ?: 0
            // 每一页的数据长度
            val pageSize = params.loadSize
            return try {
                delay(2000)
                val data = mutableListOf<PagingItemViewModel<PagingModelTest>>()
                val itemPageViewModel = PagingItemViewModel<PagingModelTest>().apply {
                    layoutRes = R.layout.item_test
                }
                itemPageViewModel.model = PagingModelTest("标题")
                data.add(itemPageViewModel)
                data.add(itemPageViewModel)
                LoadResult.Page(
                    data = data,
                    prevKey = if (page == 0) null else page - 1,
                    nextKey = page + 1
                )
            } catch (e: Exception) {
                LoadResult.Error(e)
            }

        }
    }
}