package com.dza.komikara.admin

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dza.komikara.ChapterAdapter
import com.dza.komikara.databinding.ActivityAdminDetailBinding
import com.dza.komikara.model.Komiks
import com.dza.komikara.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminDetail : AppCompatActivity() {
    private lateinit var binding: ActivityAdminDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mengambil ID detail dari Intent
        val komikId = intent.getStringExtra("komikId")
        if (!komikId.isNullOrEmpty()) {
            fetchDetailKomik(komikId)
        } else {
            Toast.makeText(this, "ID tidak ditemukan", Toast.LENGTH_SHORT).show()
        }

        binding.btnBack.setOnClickListener {
            finish()
        }

        getAllChapters()
    }

    // Fungsi untuk mengambil data detail dari API
    private fun fetchDetailKomik(id: String) {
        val apiService = ApiClient.getInstance()
        val call = apiService.getAllKomiks()

        call.enqueue(object : Callback<List<Komiks>> {
            override fun onResponse(call: Call<List<Komiks>>, response: Response<List<Komiks>>) {
                if (response.isSuccessful) {
                    val komik = response.body()?.find { it.id == id }
                    if (komik != null) {
                        // Menampilkan data detail di halaman detail
                        with(binding) {
                            detailTitle.text = komik.title
                            detailAuthor.text = komik.author
                            detailGenre.text = komik.genre
                            detailType.text = komik.type
                            detailStatus.text = komik.status
                            detailReleased.text = komik.released
                            detailSynopsis.text = komik.synopsis
                            Glide.with(this@AdminDetail).load(komik.cover).into(detailCover)
                        }
                    } else {
                        Toast.makeText(this@AdminDetail, "Komik tidak ditemukan", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@AdminDetail, "Gagal memuat data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Komiks>>, t: Throwable) {
                Toast.makeText(this@AdminDetail, "Gagal mengambil data: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getAllChapters() {
        val chapters = listOf("Chapter 1", "Chapter 2", "Chapter 3", "Chapter 4", "Chapter 5", "Chapter 6", "Chapter 7", "Chapter 8", "Chapter 9", "Chapter 10")
        val adapter = ChapterAdapter(this, chapters)

        binding.listChapter.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        getAllChapters()
    }
}
