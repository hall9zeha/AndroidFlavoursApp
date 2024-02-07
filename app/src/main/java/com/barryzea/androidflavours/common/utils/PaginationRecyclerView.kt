package com.barryzea.androidflavours.common.utils

import android.transition.Fade
import android.transition.TransitionManager
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.barryzea.androidflavours.R
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 01/02/2024.
 **/

abstract class PaginationRecyclerView(private val bottomNav:BottomNavigationView?,private val linearLayoutManager: GridLayoutManager) :RecyclerView.OnScrollListener(){
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount= linearLayoutManager.childCount
        val totalItemCount= linearLayoutManager.itemCount
        val firstVisibleItemCount= linearLayoutManager.findFirstVisibleItemPosition()

        val fade = Fade()
        fade.setDuration(200)

        //Controlamos la visibilidad de nuestro BottomNavigationView
        bottomNav?.let{
            if(dy > 0 && bottomNav.visibility == View.VISIBLE){
                bottomNav.visibility=View.GONE
            }
            else if(dy < 0 && bottomNav.visibility != View.VISIBLE) bottomNav.visibility = View.VISIBLE

        }

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