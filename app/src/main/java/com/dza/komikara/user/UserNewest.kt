package com.dza.komikara.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dza.komikara.database.KomikRoomDatabase
import com.dza.komikara.databinding.FragmentUserBookmarkBinding
import com.dza.komikara.databinding.FragmentUserNewestBinding
import com.dza.komikara.model.Komiks
import com.dza.komikara.network.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserNewest : Fragment() {

    private var _binding: FragmentUserNewestBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: UserKomikFavoritAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserNewestBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.btnSearch.setOnClickListener {
            Toast.makeText(requireContext(), "Fitur masih dalam pengembangan...", Toast.LENGTH_SHORT).show()
        }

        binding.btnNotif.setOnClickListener {
            Toast.makeText(requireContext(), "Fitur masih dalam pengembangan...", Toast.LENGTH_SHORT).show()
        }

        fetchKomiksFromApi()

        return view
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

        val gridAdapter = UserKomikGridAdapter(requireContext(), komiksList) { komik ->
            onKomikClick(komik)
        }
        binding.rvKomikNewest.apply {
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