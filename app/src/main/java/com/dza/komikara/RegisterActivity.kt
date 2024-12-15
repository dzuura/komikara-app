package com.dza.komikara

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dza.komikara.databinding.ActivityRegisterBinding
import com.dza.komikara.network.ApiClient
import com.dza.komikara.user.UserMain
import com.dza.retrofit.model.Users
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var prefManager: PrefManager
    private val apiService = ApiClient.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefManager = PrefManager.getInstance(this)

        if (prefManager.isLoggedIn()) {
            startActivity(Intent(this, UserMain::class.java))
            finish()
        }

        with(binding) {
            btnRegister.setOnClickListener {
                val username = txtUser.text.toString()
                val email = txtEmail.text.toString()
                val password = txtPassword.text.toString()

                if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(this@RegisterActivity, "Mohon isi semua data", Toast.LENGTH_SHORT).show()
                } else {
                    val newUser = Users(username = username, email = email, password = password)
                    apiService.register(newUser).enqueue(object : Callback<Void> {
                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                            if (response.isSuccessful) {
                                Toast.makeText(this@RegisterActivity, "Registrasi berhasil", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                                finish()
                            } else {
                                Toast.makeText(this@RegisterActivity, "Registrasi gagal", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            Toast.makeText(this@RegisterActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }

            txtLogin.setOnClickListener {
                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            }
        }
    }
}