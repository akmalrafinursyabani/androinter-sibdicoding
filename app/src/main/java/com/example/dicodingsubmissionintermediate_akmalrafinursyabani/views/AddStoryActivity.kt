package com.example.dicodingsubmissionintermediate_akmalrafinursyabani.views

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.location.Location
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.R
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.databinding.ActivityAddStoryBinding
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.helper.imageSizeReducer
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.helper.importFromGallery
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.helper.rotatePhoto
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.helper.validateToken
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.model.User
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.config.APIConfig
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.responses.UploadStoryResponse
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.preference.UserPreference
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.viewmodels.AddStoryViewModel
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.viewmodels.ViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import kotlin.math.ln

class AddStoryActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityAddStoryBinding
    private var getFile: File? = null
    private lateinit var token: String
    private lateinit var user: User
    private lateinit var userPreference: UserPreference

    lateinit var lat: String
    lateinit var lng: String

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.takePicture.setOnClickListener(this)

        if (!allPermissionGranted()) {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        getUserLastLocation()

        binding.fromGallery.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            val chooser = Intent.createChooser(intent, "Choose a picture or image")
            launcherGallery.launch(chooser)
        }

        binding.upload.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        if (p0?.id == R.id.take_picture) {
            if (!allPermissionGranted()) {
                ActivityCompat.requestPermissions(
                    this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
                )
            }
            val intent = Intent(this@AddStoryActivity, CameraActivity::class.java)
            launcherCameraX.launch(intent)
        } else if (p0?.id == R.id.upload) {
            if (getFile != null) {
                val addStoryViewModel: AddStoryViewModel by viewModels {
                    ViewModelFactory(
                        activity = this,
                        context = this,
                        addStoryBinding = binding
                    )
                }
                addStoryViewModel.addStory(getFile!!, lat, lng).observe(this) { result ->
                    if (result != null) {
                        when (result) {
                            is com.example.dicodingsubmissionintermediate_akmalrafinursyabani.data.Result.Loading -> {
//                            binding.loginProgressbar.visibility = View.VISIBLE
                            }
                            is com.example.dicodingsubmissionintermediate_akmalrafinursyabani.data.Result.Success -> {
//                            binding.loginProgressbar.visibility = View.GONE
                            }
                            is com.example.dicodingsubmissionintermediate_akmalrafinursyabani.data.Result.Error -> {
//                            binding.loginProgressbar.visibility = View.GONE
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionGranted()) {
                Toast.makeText(
                    this,
                    "Aplikasi tidak mendapatkan izin untuk membuka kamera!",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


    private fun checkPermissionIsGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager
            .PERMISSION_GRANTED
    }

    private fun allPermissionGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    // Launcher

    private val launcherCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = it.data?.getSerializableExtra("picture") as File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean
            getFile = myFile

            val result = rotatePhoto(
                BitmapFactory.decodeFile(myFile.path),
                isBackCamera
            )

            binding.imagePreview.setImageBitmap(result)
        }
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = importFromGallery(selectedImg, this@AddStoryActivity)
            getFile = myFile

            binding.imagePreview.setImageURI(selectedImg)
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
                    this@AddStoryActivity,
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
        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}