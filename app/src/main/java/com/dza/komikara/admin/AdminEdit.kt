package com.dza.komikara.admin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dza.komikara.databinding.ActivityAdminEditBinding
import com.dza.komikara.model.Komiks
import com.dza.komikara.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminEdit : AppCompatActivity() {
    private lateinit var binding: ActivityAdminEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ambil ID dari Intent
        val komikId = intent.getStringExtra("EXTRA_ID") ?: ""
        if (komikId.isNotEmpty()) {
            fetchDetailKomik(komikId) // Ambil data dari API berdasarkan ID
        } else {
            Toast.makeText(this, "ID tidak valid", Toast.LENGTH_SHORT).show()
            finish()
        }

        setupListeners(komikId)

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun setupListeners(komikId: String) {
        with(binding) {
            btnUpdate.setOnClickListener {
                val cover = editCover.text.toString()
                val judul = editTitle.text.toString()
                val pengarang = editAuthor.text.toString()
                val genre = editGenre.text.toString()
                val tipe = editType.text.toString()
                val status = editStatus.text.toString()
                val released = editReleased.text.toString()
                val sinopsis = editSynopsis.text.toString()

                if (validateInputs(cover, judul, pengarang, genre, tipe, status, released, sinopsis)) {
                    val updatedKomik = Komiks(
                        id = komikId,
                        cover = cover,
                        title = judul,
                        author = pengarang,
                        genre = genre,
                        type = tipe,
                        status = status,
                        released = released,
                        synopsis = sinopsis
                    )
                    updateKomikToApi(updatedKomik) // Pastikan parameter dikirim
                } else {
                    Toast.makeText(this@AdminEdit, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
                }
            }

            btnDelete.setOnClickListener {
                showDeleteConfirmationDialog(komikId)
            }
        }
    }

    private fun validateInputs(vararg inputs: String): Boolean {
        return inputs.all { it.isNotBlank() }
    }

    private fun fetchDetailKomik(id: String) {
        ApiClient.getInstance().getKomiksById(id).enqueue(object : Callback<Komiks> {
            override fun onResponse(call: Call<Komiks>, response: Response<Komiks>) {
                if (response.isSuccessful) {
                    val komik = response.body()
                    komik?.let {
                        binding.editTitle.setText(it.title)
                        binding.editAuthor.setText(it.author)
                        binding.editGenre.setText(it.genre)
                        binding.editType.setText(it.type)
                        binding.editStatus.setText(it.status)
                        binding.editReleased.setText(it.released)
                        binding.editSynopsis.setText(it.synopsis)
                        binding.editCover.setText(it.cover)
                    }
                } else {
                    Toast.makeText(this@AdminEdit, "Gagal memuat data komik", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<Komiks>, t: Throwable) {
                Toast.makeText(
                    this@AdminEdit,
                    "Kesalahan jaringan: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun updateKomikToApi(komik: Komiks) {
        ApiClient.getInstance().updateKomik(komik.id?:"", komik).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@AdminEdit, "Data berhasil diperbarui", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@AdminEdit, AdminHome::class.java))
                    finish()
                } else {
                    Toast.makeText(this@AdminEdit, "Gagal memperbarui data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@AdminEdit, "Kesalahan jaringan: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showDeleteConfirmationDialog(komikId: String) {
        AlertDialog.Builder(this@AdminEdit).apply {
            setTitle("Konfirmasi")
            setMessage("Apakah Anda yakin ingin menghapus data ini?")
            setPositiveButton("Ya") { _, _ ->
                deleteKomikFromApi(komikId)
            }
            setNegativeButton("Tidak") { dialog, _ ->
                dialog.dismiss()
            }
            create().show()
        }
    }

    private fun deleteKomikFromApi(id: String) {
        ApiClient.getInstance().deleteKomik(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@AdminEdit, "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@AdminEdit, AdminHome::class.java))
                    finish()
                } else {
                    Toast.makeText(this@AdminEdit, "Gagal menghapus data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@AdminEdit, "Kesalahan jaringan: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}