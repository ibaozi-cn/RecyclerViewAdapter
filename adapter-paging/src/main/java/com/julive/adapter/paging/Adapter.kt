package com.julive.adapter.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.paging.*
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.ConcatAdapter
import com.julive.adapter.core.DefaultViewHolder
import com.julive.adapter.core.ListAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow

class PagingListAdapter : ListAdapter<ItemPageViewModel<*>, DefaultViewHolder>() {

    private val differ = AsyncPagingDataDiffer(
        diffCallback = DiffViewModelCallBack(),
        updateCallback = AdapterListUpdateCallback(this),
        mainDispatcher = Dispatchers.Main,
        workerDispatcher = Dispatchers.Default
    )

    override fun getItem(position: Int): ItemPageViewModel<*>? {
        return differ.getItem(position)
    }

    suspend fun submitData(pagingData: PagingData<ItemPageViewModel<*>>) {
        differ.submitData(pagingData)
    }

    fun submitData(lifecycle: Lifecycle, pagingData: PagingData<ItemPageViewModel<*>>) {
        differ.submitData(lifecycle, pagingData)
    }

    fun retry() {
        differ.retry()
    }

    fun refresh() {
        differ.refresh()
    }

    override fun getItemCount() = differ.itemCount

    @OptIn(FlowPreview::class)
    val loadStateFlow: Flow<CombinedLoadStates> = differ.loadStateFlow

    fun addLoadStateListener(listener: (CombinedLoadStates) -> Unit) {
        differ.addLoadStateListener(listener)
    }

    fun removeLoadStateListener(listener: (CombinedLoadStates) -> Unit) {
        differ.removeLoadStateListener(listener)
    }

    fun withLoadStateHeader(
        header: LoadStateAdapter<*>
    ): ConcatAdapter {
        addLoadStateListener { loadStates ->
            header.loadState = loadStates.prepend
        }
        return ConcatAdapter(header, this)
    }

    fun withLoadStateFooter(
        footer: LoadStateAdapter<*>
    ): ConcatAdapter {
        addLoadStateListener { loadStates ->
            footer.loadState = loadStates.append
        }
        return ConcatAdapter(this, footer)
    }

    fun withLoadStateHeaderAndFooter(
        header: LoadStateAdapter<*>,
        footer: LoadStateAdapter<*>
    ): ConcatAdapter {
        addLoadStateListener { loadStates ->
            header.loadState = loadStates.prepend
            footer.loadState = loadStates.append
        }
        return ConcatAdapter(header, this, footer)
    }

    @ExperimentalPagingApi
    val dataRefreshFlow: Flow<Boolean> = differ.dataRefreshFlow

    @ExperimentalPagingApi
    fun addDataRefreshListener(listener: (isEmpty: Boolean) -> Unit) {
        differ.addDataRefreshListener(listener)
    }

    @ExperimentalPagingApi
    fun removeDataRefreshListener(listener: (isEmpty: Boolean) -> Unit) {
        differ.removeDataRefreshListener(listener)
    }
}

class PagingLoadStateAdapter : LoadStateAdapter<DefaultViewHolder>() {

    override fun onBindViewHolder(holder: DefaultViewHolder, loadState: LoadState) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): DefaultViewHolder {
        return DefaultViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_load_state, parent, false)
        )
    }
}