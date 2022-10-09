package com.zahirar.challengechap5

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.zahirar.challengechap5.databinding.ActivityLoginBinding
import com.zahirar.challengechap5.viewmodel.ViewModelUser
import com.zahirar.challengechap5.network.APIClient
import com.zahirar.challengechap5.model.GetUserResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class LoginActivity : AppCompatActivity() {

    lateinit var binding : ActivityLoginBinding
    lateinit var sharedPref : SharedPreferences
    lateinit var userVM: ViewModelUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = this.getSharedPreferences("user", Context.MODE_PRIVATE)

        if(sharedPref.getBoolean("isLogin",false)){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

        userVM = ViewModelProvider(this).get(ViewModelUser::class.java)

        binding.tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            var username = binding.edtUsername.editText?.text.toString()
            var password = binding.edtPassword.editText?.text.toString()
            loginProcess(username, password)
        }

        binding.btnIn.setOnClickListener {
            setLocale("id")
        }

        binding.btnEn.setOnClickListener {
            setLocale("en")
        }
    }

    fun gotoHome(){
        startActivity(Intent(this, MainActivity::class.java))
    }

    fun loginProcess(username : String, password : String) {
        APIClient.instance.getAllUser()
            .enqueue(object : Callback<List<GetUserResponseItem>> {
                override fun onResponse(
                    call: Call<List<GetUserResponseItem>>,
                    response: Response<List<GetUserResponseItem>>
                ) {
                    if (response.isSuccessful) {
                        var responseBody = response.body()
                        if (responseBody != null) {
                            for (i in 0 until responseBody.size) {
                                if (responseBody[i].username.equals(username) && responseBody[i].password.equals(password)
                                ) {
                                    var addData = sharedPref.edit()
                                    addData.putString("name", responseBody[i].name)
                                    addData.putString("id", responseBody[i].id)
                                    addData.putBoolean("isLogin", true)
                                    addData.apply()
                                    Toast.makeText(this@LoginActivity, "Login Berhasil", Toast.LENGTH_SHORT).show()
                                    gotoHome()
                                }
                            }
                        }
                    } else {
                        Toast.makeText(this@LoginActivity, "Failed to Load Data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<GetUserResponseItem>>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "Something Wrong", Toast.LENGTH_SHORT).show()
                }
            })
    }

    fun setLocale(lang: String) {
        val myLocale = Locale(lang)
        val res = resources
        val conf = res.configuration
        conf.locale = myLocale
        res.updateConfiguration(conf, res.displayMetrics)
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}