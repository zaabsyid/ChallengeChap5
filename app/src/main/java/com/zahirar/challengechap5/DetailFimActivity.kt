package com.zahirar.challengechap5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.zahirar.challengechap5.databinding.ActivityDetailFimBinding
import com.zahirar.challengechap5.viewmodel.ViewModelFilm
import kotlin.properties.Delegates

class DetailFimActivity : AppCompatActivity() {

    lateinit var binding : ActivityDetailFimBinding
    lateinit var viewModel : ViewModelFilm
    var id by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailFimBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ViewModelFilm::class.java)
        id = intent.getStringExtra("id")!!.toInt()
        setData()

        binding.btnBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    fun setData(){
        viewModel.callGetFilmById(id)
        viewModel.getFilmById(id).observe(this, Observer {
            binding.edtNamaFilm.editText?.setText(it.name)
            binding.edtSutradara.editText?.setText(it.director)
            binding.edtDeskripsi.editText?.setText(it.description)
            binding.edtLinkGambar.editText?.setText(it.image)
            Glide.with(this).load(it.image).into(binding.ivImgFilm)
        })
    }
}