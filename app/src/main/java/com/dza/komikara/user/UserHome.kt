package com.dza.komikara.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dza.komikara.databinding.FragmentUserHomeBinding
import com.dza.komikara.model.Komiks
import com.dza.komikara.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserHome : Fragment() {
    private var _binding: FragmentUserHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        fetchKomiksFromApi()
    }

    private fun setupListeners() {
        with(binding) {
            binding.btnSearch.setOnClickListener {
                Toast.makeText(requireContext(), "Fitur masih dalam pengembangan...", Toast.LENGTH_SHORT).show()
            }

            binding.btnNotif.setOnClickListener {
                Toast.makeText(requireContext(), "Fitur masih dalam pengembangan...", Toast.LENGTH_SHORT).show()
            }

            menuKPro.setOnClickListener {
                Toast.makeText(requireContext(), "Fitur masih dalam pengembangan...", Toast.LENGTH_SHORT).show()
            }

            menuHot.setOnClickListener {
                Toast.makeText(requireContext(), "Fitur masih dalam pengembangan...", Toast.LENGTH_SHORT).show()
            }

            menuRekomen.setOnClickListener {
                Toast.makeText(requireContext(), "Fitur masih dalam pengembangan...", Toast.LENGTH_SHORT).show()
            }

            menuBerwarna.setOnClickListener {
                Toast.makeText(requireContext(), "Fitur masih dalam pengembangan...", Toast.LENGTH_SHORT).show()
            }

            menuTerpopuler.setOnClickListener {
                Toast.makeText(requireContext(), "Fitur masih dalam pengembangan...", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchKomiksFromApi() {
        ApiClient.getInstance().getAllKomiks().enqueue(object : Callback<List<Komiks>> {
            override fun onResponse(call: Call<List<Komiks>>, response: Response<List<Komiks>>) {
                if (response.isSuccessful) {
                    val komiksList = response.body().orEmpty()
                    if (komiksList.isNotEmpty()) {
                        setupRecyclerViews(komiksList)
                    } else {
                        Toast.makeText(requireContext(), "Data kosong", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Gagal memuat data", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<Komiks>>, t: Throwable) {
                Toast.makeText(requireContext(), "Koneksi error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun setupRecyclerViews(komiksList: List<Komiks>) {
        val horizontalAdapter = UserKomikHorizontalAdapter(requireContext(), komiksList) { komik ->
            onKomikClick(komik)
        }

        binding.rvKomikHorizontal.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = horizontalAdapter
        }

        val gridAdapter = UserKomikGridAdapter(requireContext(), komiksList) { komik ->
            onKomikClick(komik)
        }
        binding.rvKomikGrid.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = gridAdapter
        }
    }

    private fun onKomikClick(komik: Komiks) {
        Toast.makeText(requireContext(), "Klik pada: ${komik.title}", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}