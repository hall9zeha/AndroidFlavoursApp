package com.barryzea.androidflavours

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import dagger.hilt.android.HiltAndroidApp

/**
 * Project AndroidFlavours
 * Created by Barry Zea H. on 30/01/2024.
 **/
@HiltAndroidApp
class MyApp:Application() {
    override fun onCreate() {
        super.onCreate()
        val defaultPref= PreferenceManager.getDefaultSharedPreferences(this)
        if(defaultPref.getBoolean("darkTheme",false)){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

}