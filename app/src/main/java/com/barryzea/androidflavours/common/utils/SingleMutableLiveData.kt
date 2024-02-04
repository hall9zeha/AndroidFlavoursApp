package com.barryzea.androidflavours.common.utils

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 04/02/2024.
 **/

class SingleMutableLiveData<T>:MutableLiveData<T>(){
    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner) { t ->
            if (t != null) {
                observer.onChanged(t)
                postValue(null)
            }
        }
    }
}