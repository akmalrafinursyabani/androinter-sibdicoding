package com.example.dicodingsubmissionintermediate_akmalrafinursyabani.views

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.R
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.adapter.StoriesPagingAdapter
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.databinding.ActivityMainBinding
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.model.User
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.preference.UserPreference
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.viewmodels.AuthViewModel
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.viewmodels.MainViewModel
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.viewmodels.ViewModelFactory
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.views.maps.StoriesLocationActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var user: User
    private lateinit var userPreference: UserPreference
    private lateinit var token: String

    lateinit var lat: String
    lateinit var lng: String

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.data != null && result.resultCode == LoginActivity.RESULT_CODE) {
            user = result.data?.getParcelableExtra<User>(LoginActivity.EXTRA_RESULT) as User
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fabAddStory.setOnClickListener(this)

        if (!allPermissionGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        getUserLastLocation()
        showStories()
    }

    private fun showStories() {
        val mainViewModel: MainViewModel by viewModels {
            ViewModelFactory(context = this, activity = this)
        }

        val adapter = StoriesPagingAdapter()
        binding.rvUserStory.layoutManager = LinearLayoutManager(this)
        binding.rvUserStory.adapter = adapter
        mainViewModel.story.observe(this) {
            adapter.submitData(lifecycle, it)
        }

        adapter.setOnItemClickCallback(object : StoriesPagingAdapter.OnItemClickCallback {
            override fun onItemClicked(data: String) {
                Toast.makeText(this@MainActivity, data, Toast.LENGTH_SHORT).show()
                val intent = Intent(this@MainActivity, DetailStoryActivity::class.java)
                intent.putExtra(DetailStoryActivity.EXTRA_ID, data)
                startActivity(intent)
            }

        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout_button -> {
                val authViewModel: AuthViewModel by viewModels {
                    ViewModelFactory(context = this, activity = this)
                }
                authViewModel.logout().observe(this) {}
                true
            }
            R.id.maps_menu -> {
                val intent = Intent(this@MainActivity, StoriesLocationActivity::class.java)
                launcher.launch(intent)
                true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onClick(p0: View?) {
        if (p0?.id == R.id.fab_add_story) {
            val intent = Intent(this@MainActivity, AddStoryActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    // Location Permission
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permission ->
        when {
            permission[Manifest.permission.ACCESS_FINE_LOCATION] ?: false -> {
                getUserLastLocation()
            }
            permission[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> {
                getUserLastLocation()
            }
            else -> {
                // Do something here...
            }
        }
    }

    private fun checkPermissionIsGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager
            .PERMISSION_GRANTED
    }

    private fun allPermissionGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("MissingPermission")
    private fun getUserLastLocation() {
        if (checkPermissionIsGranted(Manifest.permission.ACCESS_FINE_LOCATION) &&
            checkPermissionIsGranted(Manifest.permission.ACCESS_COARSE_LOCATION)
        ) {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
                try {
                    val getLatLng = location?.let { LatLng(it.latitude, it.longitude) }
                    lat = getLatLng?.latitude.toString()
                    lng = getLatLng?.longitude.toString()
                } catch (e: Exception) {
                    Log.e("FUSEDLOCATION", e.message.toString())
                }
            }.addOnFailureListener {
                Toast.makeText(
                    this,
                    it.message.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    companion object {
        const val TAG = "MainActivity"
        private val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}