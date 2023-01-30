package com.example.dicodingsubmissionintermediate_akmalrafinursyabani.views.maps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.databinding.ActivityStoriesLocationBinding
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.helper.validateToken
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.model.User
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.config.APIConfig
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.responses.ListStoryItemWithLocation
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.responses.StoriesWithLocationResponse
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.preference.UserPreference
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.viewmodels.DetailStoryViewModel
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.viewmodels.StoriesLocationViewModel
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.viewmodels.ViewModelFactory
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.views.MainActivity
import com.google.android.gms.maps.model.LatLngBounds
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoriesLocationActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityStoriesLocationBinding

    private lateinit var token: String
    private lateinit var user: User
    private lateinit var userPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStoriesLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

//        userPreference = UserPreference(this)
//        user = userPreference.getSession()
//        token = user.token.toString()

//        validateToken(user.token.toString(), this@StoriesLocationActivity)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true


        val storiesLocationViewModel: StoriesLocationViewModel by viewModels {
            ViewModelFactory(
                googleMap = googleMap,
                context = this,
                activity = this
            )
        }

        storiesLocationViewModel.storiesLocation()
            .observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is com.example.dicodingsubmissionintermediate_akmalrafinursyabani.data.Result.Loading -> {
//                            binding.detailstoryProgressbar.visibility = View.VISIBLE
                        }
                        is com.example.dicodingsubmissionintermediate_akmalrafinursyabani.data.Result.Success -> {
//                            binding.detailstoryProgressbar.visibility = View.GONE

                        }
                        is com.example.dicodingsubmissionintermediate_akmalrafinursyabani.data.Result.Error -> {
//                            binding.detailstoryProgressbar.visibility = View.GONE
                        }
                    }
                }
            }
    }

    companion object {
        const val TAG = "StoriesLocationActivity"
    }

}