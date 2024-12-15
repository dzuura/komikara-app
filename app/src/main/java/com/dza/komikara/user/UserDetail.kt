package com.dza.komikara.user

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dza.komikara.ChapterAdapter
import com.dza.komikara.R
import com.dza.komikara.database.KomikData
import com.dza.komikara.database.KomikRoomDatabase
import com.dza.komikara.databinding.ActivityUserDetailBinding
import com.dza.komikara.model.Komiks
import com.dza.komikara.network.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetail : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var komikRoomDatabase: KomikRoomDatabase
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        komikRoomDatabase = KomikRoomDatabase.getDatabase(this)

        // Mengambil ID detail dari Intent
        val komikId = intent.getStringExtra("komikId") ?: ""
        if (komikId.isNotEmpty()) {
            fetchDetailKomik(komikId) // Ambil data dari API berdasarkan ID
        } else {
            Toast.makeText(this, "ID tidak ditemukan atau tidak valid", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnFav.setOnClickListener {
            if (isFavorite) {
                removeFromFavorites(binding.detailTitle.text.toString())
            } else {
                addToFavorites(
                    binding.detailTitle.text.toString(),
                    binding.detailAuthor.text.toString(),
                    binding.detailGenre.text.toString(),
                    binding.detailType.text.toString(),
                    binding.detailStatus.text.toString(),
                    binding.detailReleased.text.toString(),
                    binding.detailSynopsis.text.toString(),
                    binding.detailCover.tag.toString()
                )
            }
        }

        getAllChapters()
    }

    // Fungsi untuk mengambil data detail dari API
    private fun fetchDetailKomik(id: String) {
        val apiService = ApiClient.getInstance()
        apiService.getAllKomiks().enqueue(object : Callback<List<Komiks>> {
            override fun onResponse(call: Call<List<Komiks>>, response: Response<List<Komiks>>) {
                if (response.isSuccessful) {
                    val komik = response.body()?.find { it.id == id }
                    if (komik != null) {
                        with(binding) {
                            detailTitle.text = komik.title
                            detailAuthor.text = komik.author
                            detailGenre.text = komik.genre
                            detailType.text = komik.type
                            detailStatus.text = komik.status
                            detailReleased.text = komik.released
                            detailSynopsis.text = komik.synopsis

                            Glide.with(this@UserDetail).load(komik.cover).into(detailCover)
                            detailCover.setTag(R.id.detail_cover, komik.cover) // Simpan URL gambar sebagai tag dengan ID spesifik
                        }
                        komik.id?.let { id -> checkIfFavorite(id) } ?: run {
                            Toast.makeText(this@UserDetail, "ID komik tidak ditemukan", Toast.LENGTH_SHORT).show()
                        }
                        updateFavoriteIcon()
                    } else {
                        Toast.makeText(this@UserDetail, "Komik tidak ditemukan", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@UserDetail, "Gagal memuat data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Komiks>>, t: Throwable) {
                Toast.makeText(this@UserDetail, "Gagal mengambil data: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getAllChapters() {
        val chapters = listOf("Chapter 1", "Chapter 2", "Chapter 3", "Chapter 4", "Chapter 5", "Chapter 6", "Chapter 7", "Chapter 8", "Chapter 9", "Chapter 10")
        val adapter = ChapterAdapter(this, chapters)

        binding.listChapter.adapter = adapter
    }

    private fun addToFavorites(title: String?, author: String?, genre: String?, type: String?, status: String?, released: String?, synopsis: String?, cover: String?) {
        val komiks = KomikData(
            title = title ?: "",
            author = author ?: "",
            genre = genre ?: "",
            type = type ?: "",
            status = status ?: "",
            released = released ?: "",
            synopsis = synopsis ?: "",
            cover = cover ?: ""
        )
        CoroutineScope(Dispatchers.IO).launch {
            komikRoomDatabase.komiksDao()?.insert(komiks) // Menggunakan DAO untuk menyimpan data
            withContext(Dispatchers.Main) {
                Toast.makeText(this@UserDetail, "$title added to favorites", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun removeFromFavorites(name: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            komikRoomDatabase.komiksDao()?.deleteByName(name ?: "") // Menggunakan DAO untuk menghapus data
            withContext(Dispatchers.Main) {
                Toast.makeText(this@UserDetail, "$name removed from favorites", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkIfFavorite(komikId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val komik = komikRoomDatabase.komiksDao()?.getkomikById(komikId) // Ganti dengan metode yang sesuai di DAO
            isFavorite = komik != null
            withContext(Dispatchers.Main) {
                updateFavoriteIcon()
            }
        }
    }

    private fun updateFavoriteIcon() {
        if (isFavorite) {
            binding.btnFav.setImageResource(R.drawable.ic_heart_fill) // Ikon untuk favorit
        } else {
            binding.btnFav.setImageResource(R.drawable.ic_heart) // Ikon untuk tidak favorit
        }
    }

    override fun onResume() {
        super.onResume()
        getAllChapters()
    }
}