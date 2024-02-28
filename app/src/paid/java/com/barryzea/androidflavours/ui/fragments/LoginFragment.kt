package com.barryzea.androidflavours.ui.fragments

import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.barryzea.androidflavours.R
import com.barryzea.androidflavours.common.LogoutCallBack
import com.barryzea.androidflavours.common.showSnackbar
import com.barryzea.androidflavours.databinding.FragmentLoginBinding
import com.barryzea.androidflavours.domain.entities.ValidateLoginRequest
import com.barryzea.androidflavours.ui.activities.MainActivity
import com.barryzea.androidflavours.ui.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.channels.consume
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


@AndroidEntryPoint
class LoginFragment : Fragment(){

    private var param1: String? = null
    private var param2: String? = null
    private var _bind:FragmentLoginBinding?=null
    private val bind:FragmentLoginBinding get() = _bind!!
    private val viewModel:LoginViewModel by viewModels()
    private var isLogin:Boolean=false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.let{
            _bind = FragmentLoginBinding.inflate(inflater,container,false)
            _bind?.let{
                return it.root
            }
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
        setUpObservers()

    }


    private fun setUpListeners()= with(bind){
        edtPassword.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_GO){
                if(!isLogin) {
                    validateNameAndPassword()
                    btnLogin.setLoading(true)
                    return@setOnEditorActionListener false
                }else{
                    Toast.makeText(context, "Ya hay una sesión abierta", Toast.LENGTH_SHORT).show()
                }
            }
            false
        }
        btnLogin.setOnClickListener {
            if(!isLogin) {
                btnLogin.isEnabled = true
                btnLogin.setLoading(true)
                validateNameAndPassword()
            }else{
                Toast.makeText(context, "Ya hay una sesión abierta", Toast.LENGTH_SHORT).show()
            }
        }

        tilPassword.setEndIconOnClickListener {

            if (edtPassword.inputType == InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT) {
                // Mostrar el texto
                edtPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                tilPassword.endIconDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_hide)
            } else {
                // Ocultar el texto
                edtPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                tilPassword.endIconDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_eye)
            }
            // Mueve el cursor al final del texto
            edtPassword.setSelection(edtPassword.text.toString().length)
        }
    }
    private fun setUpObservers(){
        viewModel.checkIfSessionIsCreated()
        viewModel.sessionIdPrefs.observe(viewLifecycleOwner){ sessionId->
            if(sessionId.isNotEmpty())isLogin=true
        }
        viewModel.createdSessionId.observe(viewLifecycleOwner){sessionId->
            if(sessionId.isNotEmpty()){
               bind.btnLogin.isEnabled=true
               Toast.makeText(context, getString(R.string.session_created), Toast.LENGTH_SHORT).show()
                bind.btnLogin.setLoading(false)
                viewModel.saveSessionId(sessionId)

                bind.edtUserName.setText("")
                bind.edtPassword.setText("")
                goToAccountContent()

            }
        }

        viewModel.msgInfo.observe(viewLifecycleOwner){
            bind.root.showSnackbar(it)
            bind.btnLogin.isEnabled=true
            bind.btnLogin.setLoading(false)
        }

    }
    private fun goToAccountContent(){
        findNavController().navigate(R.id.userAccountFragment)
    }
    private fun validateNameAndPassword()=with(bind){
        if(bind.edtUserName.text.toString().isEmpty()){
            edtUserName.error = getString(R.string.username_required_msg)
            edtUserName.requestFocus()
            btnLogin.setLoading(false)
        }
        else if(bind.edtPassword.text.toString().isEmpty()){
            edtPassword.error = getString(R.string.password_required_msg)
            edtPassword.requestFocus()
            btnLogin.setLoading(false)
        }
        else{
            viewModel.validateWithLogin(
                ValidateLoginRequest(bind.edtUserName.text.toString().trim(),
                    bind.edtPassword.text.toString().trim()
                )
            )
        }
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _bind=null
    }

}