package com.zahirar.challengechap5.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zahirar.challengechap5.DetailFimActivity
import com.zahirar.challengechap5.UpdateFilmActivity
import com.zahirar.challengechap5.databinding.ItemFilmBinding
import com.zahirar.challengechap5.model.GetFilmResponseItem

class FilmAdapter(var listFilm : List<GetFilmResponseItem>): RecyclerView.Adapter<FilmAdapter.ViewHolder>() {

    var onDeleteClick : ((GetFilmResponseItem) -> Unit)? = null

    class ViewHolder(var binding: ItemFilmBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmAdapter.ViewHolder {
        var view = ItemFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilmAdapter.ViewHolder, position: Int) {
        holder.binding.txtJudul.text = listFilm[position].name
        holder.binding.txtDirector.text = listFilm[position].director
        holder.binding.txtDeskripsi.text = listFilm[position].description
        Glide.with(holder.itemView).load(listFilm[position].image).into(holder.binding.imgFilm)

        holder.binding.ivEdit.setOnClickListener{
            var edit = Intent(it.context, UpdateFilmActivity::class.java)
            edit.putExtra("id",listFilm[position].id)
            it.context.startActivity(edit)
        }

        holder.binding.cardView.setOnClickListener {
            var detail = Intent(it.context, DetailFimActivity::class.java)
            detail.putExtra("id",listFilm[position].id)
            it.context.startActivity(detail)
        }

        holder.binding.ivDelete.setOnClickListener {
            onDeleteClick?.invoke(listFilm[position])
        }
    }

    override fun getItemCount(): Int {
        return listFilm.size
    }
}