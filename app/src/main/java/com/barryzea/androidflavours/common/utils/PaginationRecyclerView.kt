package com.barryzea.androidflavours.common.utils

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 01/02/2024.
 **/

abstract class PaginationRecyclerView(private val linearLayoutManager: GridLayoutManager) :RecyclerView.OnScrollListener(){
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount= linearLayoutManager.childCount
        val totalItemCount= linearLayoutManager.itemCount
        val firstVisibleItemCount= linearLayoutManager.findFirstVisibleItemPosition()

        if(!isLoading()&&!isLastPage()){
            if((visibleItemCount  + firstVisibleItemCount)>=totalItemCount - 5 && firstVisibleItemCount >=0
            ){
                loadMoreItems()
            }
        }

    }
    protected abstract fun loadMoreItems()
    abstract fun getTotalPageCount():Int
    abstract fun isLastPage():Boolean
    abstract fun isLoading():Boolean
}