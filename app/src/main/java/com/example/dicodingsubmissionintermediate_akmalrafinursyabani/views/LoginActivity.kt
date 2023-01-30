package com.example.dicodingsubmissionintermediate_akmalrafinursyabani.views

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.R
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.databinding.ActivityLoginBinding
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.model.User
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.config.APIConfig
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.responses.LoginResponse
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.service.APIService
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.preference.UserPreference
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.viewmodels.LoginViewModel
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.viewmodels.ViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var user: User


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = User("", "", "", "")

        binding.btnSignIn.setOnClickListener(this)
        binding.toRegister.setOnClickListener(this)

        playAnimation()
        binding.loginProgressbar.visibility = View.GONE

    }


    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imgWelcome, View.TRANSLATION_X, -45f, 45f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        AnimatorSet().apply {
            start()
        }
    }

    private fun isEmailFormatValid(email: CharSequence): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    override fun onClick(p0: View?) {
        if (p0?.id == R.id.btn_sign_in) {
            val getEmail = binding.emailEdittext.text.toString().trim()
            val getPassword = binding.passwordEditText.text.toString()

            if (!isEmailFormatValid(getEmail)) {
                binding.emailEdittext.error = getString(R.string.invalid_email)
                return
            }

            if (binding.passwordEditText.length() < 5) {
                binding.passwordEditText.error = "Password tidak boleh kurang dari 6 huruf!"
                return
            }

            val loginViewModel: LoginViewModel by viewModels {
                ViewModelFactory(
                    email = getEmail,
                    password = getPassword,
                    activity = this,
                    user = user,
                    loginBinding = binding,
                )
            }

            loginViewModel.login(getEmail, getPassword).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is com.example.dicodingsubmissionintermediate_akmalrafinursyabani.data.Result.Loading -> {
                            binding.loginProgressbar.visibility = View.VISIBLE
                        }
                        is com.example.dicodingsubmissionintermediate_akmalrafinursyabani.data.Result.Success -> {
                            binding.loginProgressbar.visibility = View.GONE
                        }
                        is com.example.dicodingsubmissionintermediate_akmalrafinursyabani.data.Result.Error -> {
                            binding.loginProgressbar.visibility = View.GONE
                        }
                    }
                }
            }
        }
        if (p0?.id == R.id.to_register) {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    companion object {
        const val EXTRA_RESULT = "extra_result"
        const val RESULT_CODE = 101
    }

}