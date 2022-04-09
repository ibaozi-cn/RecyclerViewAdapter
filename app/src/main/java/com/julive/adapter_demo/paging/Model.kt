package com.julive.adapter_demo.paging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.julive.adapter.core.LayoutViewModel
import com.julive.adapter.core.SameModel
import com.julive.adapter_demo.R
import kotlinx.coroutines.delay

class PagingModelTest(val title: String, override var uniqueId: String = title) : SameModel


class PagingViewModel : ViewModel() {

    val pager by lazy {
        Pager(
            config = PagingConfig(pageSize = 3, prefetchDistance = 1),
            pagingSourceFactory = { MainSource() }).flow.cachedIn(viewModelScope)
    }

    class MainSource : PagingSource<Int, LayoutViewModel<PagingModelTest>>() {
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LayoutViewModel<PagingModelTest>> {
            // 如果key是null，那就加载第0页的数据
            val page = params.key ?: 0
            // 每一页的数据长度
            val pageSize = params.loadSize
            return try {
                delay(2000)
                val data = mutableListOf<LayoutViewModel<PagingModelTest>>()
                val itemPageViewModel = LayoutViewModel<PagingModelTest>(R.layout.item_test)
                itemPageViewModel.model = PagingModelTest("标题")
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

        override fun getRefreshKey(state: PagingState<Int, LayoutViewModel<PagingModelTest>>): Int? {
            return state.anchorPosition
        }
    }
}