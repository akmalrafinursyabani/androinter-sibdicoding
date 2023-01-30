package com.example.dicodingsubmissionintermediate_akmalrafinursyabani.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.R
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.databinding.ActivityRegisterBinding
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.model.User
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.config.APIConfig
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.responses.RegisterResponse
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.viewmodels.RegisterViewModel
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.viewmodels.ViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        user = User("", "", "", "")

        binding.registerProgressBar.visibility = View.GONE
        binding.toLogin.setOnClickListener(this)
        binding.btnRegister.setOnClickListener(this)
    }

    private fun isEmailFormatValid(email: CharSequence): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    override fun onClick(p0: View?) {
        if (p0?.id == R.id.btn_register) {
            val getName = binding.nameEditText.text.toString().trim()
            val getEmail = binding.emailEdittext.text.toString().trim()
            val getPassword = binding.passwordEditText

            if (!isEmailFormatValid(getEmail)) {
                binding.emailEdittext.error = getString(R.string.invalid_email)
                return
            }

            if (getPassword.length() < 5) {
                getPassword.error = getString(R.string.invalid_password)
                return
            }

            val registerViewModel: RegisterViewModel by viewModels {
                ViewModelFactory(
                    name = getName,
                    email = getEmail,
                    password = getPassword.text.toString(),
                    activity = this,
                    user = user,
                    registerBinding = binding
                )
            }

            registerViewModel.register(
                name = getName,
                email = getEmail,
                password = getPassword.text.toString()
            ).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is com.example.dicodingsubmissionintermediate_akmalrafinursyabani.data.Result.Loading -> {
                            binding.registerProgressBar.visibility = View.VISIBLE
                        }
                        is com.example.dicodingsubmissionintermediate_akmalrafinursyabani.data.Result.Success -> {
                            binding.registerProgressBar.visibility = View.GONE
                        }
                        is com.example.dicodingsubmissionintermediate_akmalrafinursyabani.data.Result.Error -> {
                            binding.registerProgressBar.visibility = View.GONE
                        }
                    }
                }
            }
        }

        if (p0?.id == R.id.to_login) {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}