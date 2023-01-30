package com.example.dicodingsubmissionintermediate_akmalrafinursyabani.views

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.databinding.ActivityDetailStoryBinding
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.helper.validateToken
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.model.User
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.config.APIConfig
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.responses.StoryDetailResponse
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.preference.UserPreference
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.viewmodels.DetailStoryViewModel
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.viewmodels.RegisterViewModel
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.viewmodels.ViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class DetailStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailStoryBinding
    private lateinit var user: User
    private lateinit var idStory: String

    //    private lateinit var detailStoryViewModel: DetailStoryViewModel
    private val detailStoryViewModel: DetailStoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        idStory = intent.getStringExtra(EXTRA_ID).toString()


        val detailStoryViewModel: DetailStoryViewModel by viewModels {
            ViewModelFactory(
                activity = this,
                detailStoryBinding = binding
            )
        }

        detailStoryViewModel.getStoryDetail(id = idStory, context = this)
            .observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is com.example.dicodingsubmissionintermediate_akmalrafinursyabani.data.Result.Loading -> {
                            binding.detailstoryProgressbar.visibility = View.VISIBLE
                        }
                        is com.example.dicodingsubmissionintermediate_akmalrafinursyabani.data.Result.Success -> {
                            binding.detailstoryProgressbar.visibility = View.GONE
                        }
                        is com.example.dicodingsubmissionintermediate_akmalrafinursyabani.data.Result.Error -> {
                            binding.detailstoryProgressbar.visibility = View.GONE
                        }
                    }
                }
            }
    }

    companion object {
        const val EXTRA_ID = "id"
    }
}