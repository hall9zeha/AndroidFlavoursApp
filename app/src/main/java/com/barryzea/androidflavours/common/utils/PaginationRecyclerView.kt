package com.barryzea.androidflavours.common.utils

import android.transition.Fade
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.barryzea.androidflavours.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 01/02/2024.
 **/

abstract class PaginationRecyclerView(private val bottomNav:BottomNavigationView?,private val fab:FloatingActionButton?,private val layoutManager: GridLayoutManager) :RecyclerView.OnScrollListener(){
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        var slideDownAnim:Animation?=null
        var slideUpAnim:Animation?=null
        bottomNav?.let {
            slideDownAnim = AnimationUtils.loadAnimation(it.context,R.anim.slide_down)
            slideUpAnim = AnimationUtils.loadAnimation(it.context,R.anim.slide_up)
        }

        val firstVisibleItem = layoutManager.findFirstCompletelyVisibleItemPosition()
        val lastVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition()
        val visibleItemCount= layoutManager.childCount
        val totalItemCount= layoutManager.itemCount
        val firstVisibleItemCount= layoutManager.findFirstVisibleItemPosition()

        val fade = Fade()
        fade.setDuration(200)

        //Controlamos la visibilidad de nuestro BottomNavigationView y floating action button
        bottomNav?.let{
            if(dy > 0 && bottomNav.visibility == View.VISIBLE){
                bottomNav.startAnimation(slideDownAnim)
                fab?.show()
                bottomNav.visibility=View.GONE
            }
            else if(dy < 0 && bottomNav.visibility != View.VISIBLE) {
                bottomNav.startAnimation(slideUpAnim)
                fab?.hide()
                bottomNav.visibility = View.VISIBLE
            }
        }
        fab?.let {
           if (firstVisibleItem == 0) {
                fab.hide()
                bottomNav?.visibility = View.VISIBLE
           }
            //Si se ha llegado al final de la lista ocultar el FAB
          if(isLastPage() && (totalItemCount - 1)==lastVisibleItem)fab.hide()
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