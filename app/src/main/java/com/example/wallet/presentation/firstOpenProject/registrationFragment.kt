package com.example.wallet.presentation.firstOpenProject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.wallet.R
import com.example.wallet.data.sharedPreferences.AuthUtils
import com.example.wallet.databinding.FragmentRegistrationBinding
import com.google.firebase.auth.FirebaseAuth

class registrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistrationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegistration.setOnClickListener {
            val email = binding.edEmail.text.toString()
            val password = binding.edPassword.toString()

            if (email.isNotEmpty() && password.length >= 6){
                auth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful){
                            AuthUtils(requireContext()).setLogeIn(true)
                            findNavController().navigate(R.id.action_registrationFragment_to_helloWindow)
                        }else{
                            Toast.makeText(requireContext(), "Ошибка регистрации: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }else{
                Toast.makeText(requireContext(), "Введите корректные данные", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}