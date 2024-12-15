package com.dza.komikara.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dza.komikara.database.KomikRoomDatabase
import com.dza.komikara.databinding.FragmentUserFavoritBinding
import kotlinx.coroutines.launch

class UserFavorit : Fragment() {
    private lateinit var binding: FragmentUserFavoritBinding
    private lateinit var userBookmarkAdapter: UserKomikFavoritAdapter
    private lateinit var komikRoomDatabase: KomikRoomDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserFavoritBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize Room Database
        komikRoomDatabase = KomikRoomDatabase.getDatabase(requireContext())

        // Setup RecyclerView
//        userBookmarkAdapter = UserKomikFavoritAdapter { komik ->
//        }
//
//        binding.recyclerView.apply {
//            layoutManager = LinearLayoutManager(context)
//            adapter = userBookmarkAdapter
//        }
//
//        // Load data from Room Database
//        loadUserBookmarks()
    }

//    private fun loadUserBookmarks() {
//        lifecycleScope.launch {
//            val userBookmarks = komikRoomDatabase.komiksDao()?.getAllkomik() ?: emptyList()
//            userBookmarkAdapter.submitList(userBookmarks)
//        }
//    }
}
