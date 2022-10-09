package com.zahirar.challengechap5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.zahirar.challengechap5.databinding.ActivityAddFilmBinding
import com.zahirar.challengechap5.viewmodel.ViewModelFilm

class AddFilmActivity : AppCompatActivity() {

    lateinit var binding :ActivityAddFilmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFilmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnTambahFilm.setOnClickListener {
            val name = binding.edtNamaFilm.editText?.text.toString()
            val image = binding.edtLinkGambar.editText?.text.toString()
            val director = binding.edtSutradara.editText?.text.toString()
            val desc = binding.edtDeskripsi.editText?.text.toString()
            addFilm(name,image,director,desc)
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    fun addFilm(name : String, image : String, director : String, desc : String){
        val viewModel = ViewModelProvider(this).get(ViewModelFilm::class.java)
        viewModel.callPostApiFilm(name,image,director,desc)
        viewModel.postLiveDataFilm().observe(this,{
            if(it != null){
                Toast.makeText(this,"Tambah Film Berhasil", Toast.LENGTH_SHORT).show()
            }
        })
    }
}