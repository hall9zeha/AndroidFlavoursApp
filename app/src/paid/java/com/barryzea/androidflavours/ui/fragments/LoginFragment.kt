package com.barryzea.androidflavours.ui.fragments

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.barryzea.androidflavours.R
import com.barryzea.androidflavours.databinding.FragmentLoginBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class LoginFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private var _bind:FragmentLoginBinding?=null
    private val bind:FragmentLoginBinding get() = _bind!!

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
                validateNameAndPassword()
                return@setOnEditorActionListener false
            }
            false
        }
        btnLogin.setOnClickListener { validateNameAndPassword() }
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
            Toast.makeText(context, "Todo correcto", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _bind=null
    }
}