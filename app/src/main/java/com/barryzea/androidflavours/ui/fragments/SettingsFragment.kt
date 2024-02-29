package com.barryzea.androidflavours.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.barryzea.androidflavours.BuildConfig
import com.barryzea.androidflavours.R
import com.barryzea.androidflavours.databinding.AboutThisLayoutBinding
import com.barryzea.androidflavours.ui.activities.MainActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        setUpListeners()
    }
    private fun setUpListeners(){

        val themePrefs = findPreference<SwitchPreferenceCompat>("darkTheme")
        val aboutThisPref = findPreference<Preference>("aboutThis")

        themePrefs?.setOnPreferenceChangeListener { preference, newValue ->
            if(newValue as Boolean){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            true
        }
        aboutThisPref?.setOnPreferenceClickListener {
            showAboutThisDialog()
            true
        }
    }
    private fun showAboutThisDialog(){
        val dialogBind = AboutThisLayoutBinding.inflate(layoutInflater)
        dialogBind.tvAboutThis.text= getString(R.string.about_this_msg,BuildConfig.VERSION_NAME)
        MaterialAlertDialogBuilder(requireContext())
            .setView(dialogBind.root)
            .show()
    }
    override fun onResume() {
        super.onResume()
        activity?.let {
            (activity as? MainActivity)?.bind?.ctlHeader?.visibility = View.GONE
        }
    }
}