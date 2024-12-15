package com.dza.komikara.admin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dza.komikara.R
import com.dza.komikara.databinding.ActivityAdminAddBinding
import com.dza.komikara.model.Komiks
import com.dza.komikara.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminAdd : AppCompatActivity() {
    private lateinit var binding: ActivityAdminAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() {
        with(binding) {
            btnAdd.setOnClickListener {
                val cover = addCover.text.toString()
                val judul = addTitle.text.toString()
                val pengarang = addAuthor.text.toString()
                val genre = addGenre.text.toString()
                val tipe = addType.text.toString()
                val status = addStatus.text.toString()
                val released = addReleased.text.toString()
                val sinopsis = addSynopsis.text.toString()

                if (validateInputs(cover, judul, pengarang, genre, tipe, status, released, sinopsis)) {
                    val komik = Komiks(
                        title = judul,
                        author = pengarang,
                        genre = genre,
                        type = tipe,
                        status = status,
                        released = released,
                        synopsis = sinopsis,
                        cover = cover
                    )
                    addKomikToApi(komik)
                } else {
                    Toast.makeText(this@AdminAdd, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
                }
            }

            btnBack.setOnClickListener {
                finish()
            }
        }
    }

    private fun validateInputs(vararg inputs: String): Boolean {
        return inputs.all { it.isNotBlank() }
    }

    private fun addKomikToApi(komik: Komiks) {
        ApiClient.getInstance().addKomiks(komik).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@AdminAdd, "Komik berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@AdminAdd, AdminHome::class.java))
                    setEmptyField()
                } else {
                    val errorBody = response.errorBody()?.string()
                    Toast.makeText(this@AdminAdd, "Gagal: $errorBody", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@AdminAdd, "Kesalahan jaringan: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setEmptyField() {
        with(binding) {
            addCover.text.clear()
            addTitle.text.clear()
            addAuthor.text.clear()
            addGenre.text.clear()
            addType.text.clear()
            addStatus.text.clear()
            addReleased.text.clear()
            addSynopsis.text.clear()
        }
    }
}
