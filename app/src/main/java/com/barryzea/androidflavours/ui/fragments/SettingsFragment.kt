package com.barryzea.androidflavours.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.barryzea.androidflavours.R
import com.barryzea.androidflavours.ui.activities.MainActivity

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        setUpListeners()
    }
    private fun setUpListeners(){

        val themePrefs = findPreference<SwitchPreferenceCompat>("darkTheme")
        themePrefs?.setOnPreferenceChangeListener { preference, newValue ->
            if(newValue as Boolean){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            true
        }
    }

    override fun onResume() {
        super.onResume()
        activity?.let {
            (activity as? MainActivity)?.bind?.ctlHeader?.visibility = View.GONE
        }
    }
}