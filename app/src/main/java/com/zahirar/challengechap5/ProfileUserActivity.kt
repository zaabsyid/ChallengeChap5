package com.zahirar.challengechap5

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.zahirar.challengechap5.databinding.ActivityProfileUserBinding
import com.zahirar.challengechap5.viewmodel.ViewModelFilm
import com.zahirar.challengechap5.viewmodel.ViewModelUser
import kotlin.properties.Delegates

class ProfileUserActivity : AppCompatActivity() {

    lateinit var binding : ActivityProfileUserBinding
    lateinit var sharedPref : SharedPreferences
    lateinit var editor : SharedPreferences.Editor
    lateinit var viewModel : ViewModelUser
    var id by Delegates.notNull<Int>()
    lateinit var oldPassword : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ViewModelUser::class.java)
        sharedPref = this.getSharedPreferences("user", Context.MODE_PRIVATE)
        id = sharedPref.getString("id", "")!!.toInt()

        editor = sharedPref.edit()

        getDataUser()

        binding.btnBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        binding.btnLogout.setOnClickListener {
            logout()
        }
        binding.btnUpdate.setOnClickListener {
            updateUser()
        }
    }

    fun getDataUser(){
        viewModel.callGetUserById(id)
        viewModel.getLiveDataUserId().observe(this, Observer {
            if (it != null) {
                binding.edtUsername.editText?.setText(it.username)
                binding.edtFullname.editText?.setText(it.name)
                binding.edtPassword.editText?.setText(it.password)
                binding.edtKonfirmasiPassword.editText?.setText(it.password)
                oldPassword = it.password.toString()
            }
        })
    }

    fun logout(){
        AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Are you sure want to logout?")
            .setPositiveButton("Yes") { dialog, which ->
                editor.clear()
                editor.apply()
                startActivity(Intent(this, LoginActivity::class.java))
            }
            .setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }

    fun updateUser(){
        val name = binding.edtFullname.editText?.text.toString()
        val username = binding.edtUsername.editText?.text.toString()
        val password = binding.edtPassword.editText?.text.toString()
        val passwordConfirm = binding.edtKonfirmasiPassword.editText?.text.toString()
        if(password == passwordConfirm){
            viewModel.updateApiUsers(id, name, username, password)
            viewModel.getliveDataUpdateUserId().observe(this, Observer {
                if (it != null) {
                    if(!oldPassword.equals(password)){
                        logout()
                    }
                    Toast.makeText(this, "Update Data Success", Toast.LENGTH_SHORT).show()
                }
            })
        } else{
            Toast.makeText(this, "Password not match", Toast.LENGTH_SHORT).show()
        }
    }
}