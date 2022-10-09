package com.zahirar.challengechap5

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.zahirar.challengechap5.databinding.ActivityRegisterBinding
import com.zahirar.challengechap5.viewmodel.ViewModelUser

class RegisterActivity : AppCompatActivity() {

    lateinit var binding : ActivityRegisterBinding
    lateinit var userVM : ViewModelUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userVM = ViewModelProvider(this).get(ViewModelUser::class.java)

        binding.btnRegister.setOnClickListener {
            registerUser()
        }
    }

    fun registerUser(){
        val name = binding.edtFullname.editText?.text.toString()
        val username = binding.edtUsername.editText?.text.toString()
        val password = binding.edtPassword.editText?.text.toString()
        val passwordConfirm = binding.edtKonfirmasiPassword.editText?.text.toString()

        if(name.isEmpty() || username.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty()) {
            Toast.makeText(this, "Please fill all the field", Toast.LENGTH_SHORT).show()
        }else{
            if(password == passwordConfirm){
                userVM.callPostApiUser(name, username, password)
                userVM.postLiveDataUser().observe(this) {
                    if (it != null) {
                        Toast.makeText(this, "Registration Success", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            } else{
                Toast.makeText(this, "Password not match", Toast.LENGTH_SHORT).show()
            }
        }
    }
}