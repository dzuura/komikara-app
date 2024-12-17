package com.dza.komikara.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dza.komikara.database.KomikData
import com.dza.komikara.databinding.ItemUserKomikGridBinding

class UserKomikFavoritAdapter(
    private var komikList: List<KomikData>,
    private val onItemClick: (KomikData) -> Unit
) : RecyclerView.Adapter<UserKomikFavoritAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemUserKomikGridBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(komik: KomikData) {
            binding.txtTitle.text = komik.title
            Glide.with(binding.root.context).load(komik.cover).into(binding.cover)
            itemView.setOnClickListener {
                onItemClick(komik)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserKomikGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(komikList[position])
    }

    override fun getItemCount(): Int = komikList.size

    fun updateList(newList: List<KomikData>) {
        komikList = newList
        notifyDataSetChanged()
    }
}

