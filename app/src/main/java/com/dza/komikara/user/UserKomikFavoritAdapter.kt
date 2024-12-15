package com.dza.komikara.user

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dza.komikara.database.KomikData
import com.dza.komikara.databinding.ItemUserKomikGridBinding

class UserKomikFavoritAdapter(
    private var komikList: List<KomikData>,
    private val onItemClick: (KomikData) -> Unit
) : RecyclerView.Adapter<UserKomikFavoritAdapter.KomikViewHolder>() {

    inner class KomikViewHolder(private val binding: ItemUserKomikGridBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(komik: KomikData) {
            binding.txtTitle.text = komik.title
            Glide.with(binding.cover.context).load(komik.cover).into(binding.cover)

            // Event klik item untuk membuka UserDetail
            binding.root.setOnClickListener {
                val context = binding.root.context
                val intent = Intent(context, UserDetail::class.java).apply {
                    putExtra("komikId", komik.id.toString())
                    putExtra("title", komik.title)
                    putExtra("author", komik.author)
                    putExtra("synopsis", komik.synopsis)
                    putExtra("cover", komik.cover)
                }
                context.startActivity(intent)
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

    override fun getItemCount(): Int = komikList.size

    fun updateList(newList: List<KomikData>) {
        komikList = newList
        notifyDataSetChanged()
    }
}
