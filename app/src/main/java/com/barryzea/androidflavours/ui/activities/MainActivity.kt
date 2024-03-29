package com.barryzea.androidflavours.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.PopupMenu
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.barryzea.androidflavours.R
import com.barryzea.androidflavours.common.LogoutCallBack
import com.barryzea.androidflavours.common.showSnackbar
import com.barryzea.androidflavours.databinding.ActivityMainBinding
import com.barryzea.androidflavours.ui.fragments.UserAccountFragmentDirections
import com.barryzea.androidflavours.ui.viewmodel.LoginViewModel
import com.barryzea.androidflavours.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(){
    private  var _bind:ActivityMainBinding?=null
    internal  val bind:ActivityMainBinding get() = _bind!!
    private val viewModel: MainViewModel by viewModels()
    private val loginViewModel:LoginViewModel by viewModels()
    private lateinit var navController:NavController
    private var popupMenu:PopupMenu?=null
    private var isLogin = false
    private var sessionId:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            CoroutineScope(Dispatchers.IO).launch {
                delay(2000)
                setKeepOnScreenCondition{false}
            }
        }

        _bind= ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)
        setUpBottomNav()
        setUpObservers()
        setupPopupMenu()
        setUpListeners()
        onBackPressedDispatcher()
    }
    private fun setUpBottomNav(){
        val navGraphIds = listOf(R.navigation.nav_graph)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val controller = bind.bottomNav.setupWithNavController(navController!!)
        navController.addOnDestinationChangedListener{_,destination,_->
            //Si ya hemos iniciado sesión la hacer click en el item login del bottomNav
            //nos mostrará el fragmento de detalle de nuestra cuenta, caso contrario
            //mostrará el comportamiento habitual y nos llevará al loginFragment
            //Al agregar programaticamente este nuevo fragmento navController.navigateUp(), también el evento backPressed
            //no funcionarán(no encontré la causa aún) así que debemos sobreescribir el evento onBackPressed
            //con la función onBackPressedDispatcher()
            if(destination.id==R.id.loginFragment && isLogin){
                navController.navigate(R.id.userAccountFragment)
            }
        }
    }
    private fun setUpListeners()=with(bind){
        ivSession.setOnClickListener {
            if(isLogin) popupMenu?.show()
        }
    }
    private fun setUpObservers(){
        viewModel.getPreferences()
        viewModel.preferences.observe(this){preferences->
            if(preferences.sessionId!!.isNotEmpty()){
                loginViewModel.fetchUserDetail(preferences.sessionId)
                sessionId=preferences.sessionId
                isLogin=true
            }else{
                bind.tvUsername.text=""
                isLogin=false
                sessionId=null
            }
        }
        loginViewModel.userDetail.observe(this){
            bind.tvUsername.text=it.username
        }
        loginViewModel.logoutSuccess.observe(this){logout->
            if(logout){
                viewModel.clearPreferences()
                isLogin=false
                val currentDest = navController.currentDestination
                if(currentDest?.id == R.id.userAccountFragment){
                    val goToLoginAction=UserAccountFragmentDirections.actionGoToLogin()
                    val navOptions = NavOptions.Builder()
                        .setPopUpTo(R.id.loginFragment, true) // Elimina el fragmento anterior del back stack
                        .build()
                   navController.navigate(goToLoginAction,navOptions)
                }
            }
        }
        viewModel.infoMsg.observe(this){  bind.root.showSnackbar(it)}
        loginViewModel.msgInfo.observe(this){bind.root.showSnackbar(it)}
    }
    /*override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.homeFragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }*/
    private fun setupPopupMenu(){
        popupMenu = PopupMenu(this,bind.ivSession)
        popupMenu?.inflate(R.menu.session_menu)
        popupMenu?.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.logoutItem -> {
                    sessionId?.let{
                        loginViewModel.logout(it)
                      }
                    true
                }
                else -> false
            }
        }
    }
   private fun onBackPressedDispatcher(){
       onBackPressedDispatcher.addCallback(this, object:OnBackPressedCallback(true){
           override fun handleOnBackPressed() {

               val currentDestinationId = navController.currentDestination?.id
               when(currentDestinationId){
                   R.id.userAccountFragment->navController.navigate(R.id.action_userAccountFragment_to_homeFragment)
                   R.id.homeFragment->finish()
                   else->navController.navigateUp()
               }

           }
       })
   }
}