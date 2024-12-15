package com.dza.komikara.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.dza.komikara.database.KomikRoomDatabase
import com.dza.komikara.databinding.FragmentUserBookmarkBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserBookmark : Fragment() {

    private var _binding: FragmentUserBookmarkBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: UserKomikFavoritAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserBookmarkBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.btnSearch.setOnClickListener {
            Toast.makeText(requireContext(), "Fitur masih dalam pengembangan...", Toast.LENGTH_SHORT).show()
        }

        binding.btnNotif.setOnClickListener {
            Toast.makeText(requireContext(), "Fitur masih dalam pengembangan...", Toast.LENGTH_SHORT).show()
        }

        setupRecyclerView()
        loadDataFromDatabase()

        return view
    }

    private fun setupRecyclerView() {
        adapter = UserKomikFavoritAdapter(emptyList()) { komik ->
            // Tampilkan detail atau aksi lainnya saat item diklik
            Toast.makeText(requireContext(), "${komik.title}", Toast.LENGTH_SHORT).show()
        }
        binding.rvKomikBookmark.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.rvKomikBookmark.adapter = adapter
    }

    private fun loadDataFromDatabase() {
        val dao = KomikRoomDatabase.getDatabase(requireContext()).komiksDao()

        lifecycleScope.launch(Dispatchers.IO) {
            val komikList = dao?.getAllkomik()

            // Pindahkan ke main thread untuk update UI
            withContext(Dispatchers.Main) {
                komikList?.let { adapter.updateList(it) }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}