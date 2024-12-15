package com.dza.komikara.admin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dza.komikara.LoginActivity
import com.dza.komikara.PrefManager
import com.dza.komikara.databinding.ActivityAdminHomeBinding
import com.dza.komikara.model.Komiks
import com.dza.komikara.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminHome : AppCompatActivity() {
    private lateinit var binding: ActivityAdminHomeBinding
    private lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefManager = PrefManager.getInstance(this)

        binding.rvListKomik.layoutManager = LinearLayoutManager(this)

        setupListeners()
        fetchKomiksFromApi()
    }

    private fun setupListeners() {
        with(binding) {
            btnLogout.setOnClickListener {
                prefManager.clear()
                startActivity(Intent(this@AdminHome, LoginActivity::class.java))
                finish()
            }

            btnSearch.setOnClickListener {
                Toast.makeText(this@AdminHome, "Fitur masih dalam pengembangan...", Toast.LENGTH_SHORT).show()
            }

            btnAdd.setOnClickListener {
                startActivity(Intent(this@AdminHome, AdminAdd::class.java))
            }
        }
    }

    private fun fetchKomiksFromApi() {
        ApiClient.getInstance().getAllKomiks().enqueue(object : Callback<List<Komiks>> {
            override fun onResponse(call: Call<List<Komiks>>, response: Response<List<Komiks>>) {
                if (response.isSuccessful) {
                    val komiksList = response.body() ?: listOf()
                    updateRecyclerView(komiksList)
                } else {
                    Toast.makeText(this@AdminHome, "Gagal memuat data", Toast.LENGTH_LONG).show()
                    Log.e("AdminHome", "Response error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<List<Komiks>>, t: Throwable) {
                Toast.makeText(this@AdminHome, "Koneksi error", Toast.LENGTH_LONG).show()
                Log.e("AdminHome", "Failure error: ${t.message}")
            }
        })
    }

    override fun onResume() {
        super.onResume()
        fetchKomiksFromApi()
    }

    private fun updateRecyclerView(komikList: List<Komiks>) {
        val adapter = AdminKomikAdapter(this, komikList.toMutableList()) { komik ->
            // Handle item click
            val intent = Intent(this, AdminDetail::class.java).apply {
                putExtra("komikId", komik.id)
                putExtra("komikTitle", komik.title)
                putExtra("komikAuthor", komik.author)
                putExtra("komikGenre", komik.genre)
                putExtra("komikType", komik.type)
                putExtra("komikStatus", komik.status)
                putExtra("komikReleased", komik.released)
                putExtra("komikSynopsis", komik.synopsis)
                putExtra("komikCover", komik.cover)
            }
            startActivity(intent)
        }
        binding.rvListKomik.adapter = adapter
    }
}
