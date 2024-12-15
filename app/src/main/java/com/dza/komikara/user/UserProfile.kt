package com.dza.komikara.user

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dza.komikara.LoginActivity
import com.dza.komikara.PrefManager
import com.dza.komikara.databinding.FragmentUserProfileBinding

class UserProfile : Fragment() {
    private lateinit var prefManager: PrefManager
    private var _binding: FragmentUserProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefManager = PrefManager.getInstance(requireContext())

        val username = prefManager.getUsername()
        val email = prefManager.getEmail()

        binding.txtUsername.text = username
        binding.txtEmail.text = email

        binding.btnLogout.setOnClickListener {
            prefManager.clear()
            prefManager.setLoggedIn(false)
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
