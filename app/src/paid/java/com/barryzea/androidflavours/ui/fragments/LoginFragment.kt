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
import com.barryzea.androidflavours.R
import com.barryzea.androidflavours.common.showSnackbar
import com.barryzea.androidflavours.databinding.FragmentLoginBinding
import com.barryzea.androidflavours.domain.entities.CreateSessionRequest
import com.barryzea.androidflavours.domain.entities.ValidateLoginRequest
import com.barryzea.androidflavours.ui.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private var _bind:FragmentLoginBinding?=null
    private val viewModel:LoginViewModel by viewModels()
    private val bind:FragmentLoginBinding get() = _bind!!
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
        viewModel.checkIsSessionIsCreated()
        viewModel.sessionCreatedId.observe(viewLifecycleOwner){sessionId->
            if(sessionId.isNotEmpty())isLogin=true
        }
        viewModel.newToken.observe(viewLifecycleOwner){
            it?.let{token->
                Log.e("1-NEW_TOKEN",token )
                viewModel.validateWithLogin(
                    ValidateLoginRequest(bind.edtUserName.text.toString().trim(),
                        bind.edtPassword.text.toString().trim(),
                        token
                        )
                )
            }
        }
        viewModel.authResponse.observe(viewLifecycleOwner){
            it?.let {responseAuth->
                Log.e("2-ALLOW_TOKEN",responseAuth.token.toString() )
                viewModel.createSession(CreateSessionRequest(responseAuth.token))
            }
        }
        viewModel.sessionId.observe(viewLifecycleOwner){
            it?.let{sessionId->
               bind.btnLogin.isEnabled=true
                Toast.makeText(context, "Sesión creada", Toast.LENGTH_SHORT).show()
                Log.e("3-SESSION_ID",sessionId)
                bind.btnLogin.setLoading(false)
                viewModel.saveSessionId(sessionId)
                viewModel.checkIsSessionIsCreated()
            }
        }
        viewModel.msgInfo.observe(viewLifecycleOwner){
            bind.root.showSnackbar(it)
            bind.btnLogin.isEnabled=true
            bind.btnLogin.setLoading(false)
        }
    }
    private fun validateNameAndPassword()=with(bind){
        if(bind.edtUserName.text.toString().isEmpty()){
            edtUserName.error = "Nombre requerido"
            edtUserName.requestFocus()
        }
        else if(bind.edtPassword.text.toString().isEmpty()){
            edtPassword.error = "Password requerido"
            edtPassword.requestFocus()
        }
        else{
            viewModel.requestNewToken()

        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _bind=null
    }
}