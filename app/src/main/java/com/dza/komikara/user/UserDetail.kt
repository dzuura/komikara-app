package com.dza.komikara.user

import android.os.Bundle
import android.util.Log
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

        val komikId = intent.getStringExtra("komikId")
        val isLocal = intent.getBooleanExtra("isLocal", false)

        if (komikId.isNullOrEmpty()) {
            Toast.makeText(this, "ID tidak ditemukan atau tidak valid", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        if (isLocal) {
            fetchLocalKomik(komikId)
        } else {
            fetchDetailKomik(komikId)
        }

        binding.btnBack.setOnClickListener { finish() }

        binding.btnFav.setOnClickListener {
            val coverTag = binding.detailCover.tag?.toString() ?: "https://example.com/default-cover.jpg"
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
                    coverTag
                )
            }
        }

        getAllChapters()
    }

    private fun fetchDetailKomik(id: String) {
        val apiService = ApiClient.getInstance()
        apiService.getKomiksById(id).enqueue(object : Callback<Komiks> {
            override fun onResponse(call: Call<Komiks>, response: Response<Komiks>) {
                if (response.isSuccessful) {
                    val komik = response.body()
                    komik?.let {
                        with(binding) {
                            detailTitle.text = it.title
                            detailAuthor.text = it.author
                            detailGenre.text = it.genre
                            detailType.text = it.type
                            detailStatus.text = it.status
                            detailReleased.text = it.released
                            detailSynopsis.text = it.synopsis

                            Glide.with(this@UserDetail).load(it.cover).into(detailCover)
                            detailCover.tag = it.cover // Simpan URL cover dengan benar
                        }
                        checkIfFavorite(id)
                    }
                } else {
                    Toast.makeText(this@UserDetail, "Gagal memuat data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Komiks>, t: Throwable) {
                Toast.makeText(this@UserDetail, "Gagal mengambil data: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getAllChapters() {
        val chapters = listOf("Chapter 1", "Chapter 2", "Chapter 3", "Chapter 4", "Chapter 5", "Chapter 6", "Chapter 7", "Chapter 8", "Chapter 9", "Chapter 10")
        val adapter = ChapterAdapter(this, chapters)
        binding.listChapter.adapter = adapter
    }

    private fun fetchLocalKomik(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val komik = komikRoomDatabase.komiksDao()?.getkomikById(id.toInt())
            withContext(Dispatchers.Main) {
                komik?.let {
                    binding.detailTitle.text = it.title
                    binding.detailAuthor.text = it.author
                    binding.detailGenre.text = it.genre
                    binding.detailType.text = it.type
                    binding.detailStatus.text = it.status
                    binding.detailReleased.text = it.released
                    binding.detailSynopsis.text = it.synopsis

                    Glide.with(this@UserDetail).load(it.cover).into(binding.detailCover)
                    binding.detailCover.tag = it.cover

                    isFavorite = true
                    updateFavoriteIcon()
                } ?: run {
                    Toast.makeText(this@UserDetail, "Komik tidak ditemukan di database", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }

    private fun addToFavorites(
        title: String?,
        author: String?,
        genre: String?,
        type: String?,
        status: String?,
        released: String?,
        synopsis: String?,
        cover: String?
    ) {
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
            val insertedId = komikRoomDatabase.komiksDao()?.insert(komiks)
            Log.d("FavoriteDebug", "Inserted ID: $insertedId, Title: ${komiks.title}, Cover: ${komiks.cover}")
            withContext(Dispatchers.Main) {
                isFavorite = true
                updateFavoriteIcon()
                Toast.makeText(this@UserDetail, "$title added to favorites", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun removeFromFavorites(name: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            komikRoomDatabase.komiksDao()?.deleteByName(name ?: "")
            withContext(Dispatchers.Main) {
                isFavorite = false
                updateFavoriteIcon()
                Toast.makeText(this@UserDetail, "$name removed from favorites", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkIfFavorite(komikId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            // Cari komik berdasarkan judul
            val komik = komikRoomDatabase.komiksDao()?.getkomikByTitle(binding.detailTitle.text.toString())

            withContext(Dispatchers.Main) {
                isFavorite = komik != null
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