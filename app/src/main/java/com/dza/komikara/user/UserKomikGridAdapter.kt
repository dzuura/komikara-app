package com.dza.komikara.user

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dza.komikara.databinding.ItemUserKomikGridBinding
import com.dza.komikara.model.Komiks

class UserKomikGridAdapter(
    private val context: Context,
    private val komikList: List<Komiks>,
    private val onItemClick: (Komiks) -> Unit
) : RecyclerView.Adapter<UserKomikGridAdapter.KomikViewHolder>() {

    inner class KomikViewHolder(val binding: ItemUserKomikGridBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(komik: Komiks) {
            with(binding) {
                txtTitle.text = komik.title
                Glide.with(cover.context).load(komik.cover).into(cover)

                root.setOnClickListener {
                    val intent = Intent(context, UserDetail::class.java).apply {
                        putExtra("komikId", komik.id)
                        putExtra("title", komik.title)
                        putExtra("author", komik.author)
                        putExtra("synopsis", komik.synopsis)
                        putExtra("cover", komik.cover)
                    }
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KomikViewHolder {
        val binding = ItemUserKomikGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return KomikViewHolder(binding)
    }

    override fun onBindViewHolder(holder: KomikViewHolder, position: Int) {
        holder.bind(komikList[position])
    }

    override fun getItemCount() = komikList.size
}