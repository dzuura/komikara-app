package com.dza.komikara.admin

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dza.komikara.databinding.ItemAdminKomikBinding
import com.dza.komikara.model.Komiks

class AdminKomikAdapter(
    private val context: Context,
    private val listKomiks: List<Komiks>,
    private val onItemClick: (Komiks) -> Unit
) : RecyclerView.Adapter<AdminKomikAdapter.KomikViewHolder>() {

    inner class KomikViewHolder(private val binding: ItemAdminKomikBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(komik: Komiks) {
            with(binding) {
                txtTitle.text = komik.title
                txtAuthor.text = komik.author
                txtGenre.text = komik.genre
                txtType.text = komik.type
                txtStatus.text = komik.status
                txtReleased.text = komik.released
                Glide.with(cover.context).load(komik.cover).into(cover)

                root.setOnClickListener {
                    onItemClick(komik)
                }

                btnEdit.setOnClickListener {
                    val intent = Intent(context, AdminEdit::class.java).apply {
                        putExtra("EXTRA_ID", komik.id)
                    }
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KomikViewHolder {
        val binding = ItemAdminKomikBinding.inflate(LayoutInflater.from(context), parent, false)
        return KomikViewHolder(binding)
    }

    override fun onBindViewHolder(holder: KomikViewHolder, position: Int) {
        holder.bind(listKomiks[position])
    }

    override fun getItemCount(): Int = listKomiks.size
}
