package com.example.dicodingsubmissionintermediate_akmalrafinursyabani.data

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.databinding.ActivityDetailStoryBinding
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.databinding.ActivityStoriesLocationBinding
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.helper.getSharedPref
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.helper.validateToken
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.config.APIConfig
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.responses.ListStoryItemWithLocation
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.network.responses.StoriesWithLocationResponse
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.views.LoginActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

class StoriesLocationRepository(
    private val context: Context,
    private val activity: Activity,
    private val googleMap: GoogleMap
) {
    private val boundsBuilder = LatLngBounds.Builder()

    fun storiesMarkerBounds(): LiveData<Result<StoriesWithLocationResponse>> =
        liveData {

            val getToken = getSharedPref(context = context!!)
            validateToken(getToken, activity!!)

            emit(Result.Loading)
            try {
                // jika error unable to create call adapter, jangan lupa add suspend/hapus method Call (retrofit)
                val response = APIConfig.getAPIService().getStoriesWithLocation(
                    token =
                    "Bearer $getToken"
                )
                val listStoryLocation = response.listStory
                setStoriesLocationMarker(location = listStoryLocation)

                emit(Result.Success(response))

            } catch (e: Exception) {
                Log.d(
                    "StoriesLocationRepository",
                    "StoriesLocation response: ${e.message.toString()} "
                )
                emit(Result.Error(e.message.toString()))
            }
        }

    private fun setStoriesLocationMarker(
        location: List<ListStoryItemWithLocation>
    ) {
        location.forEach { storiesLocation ->
            val latLng = LatLng(storiesLocation.lat, storiesLocation.lon)
            googleMap.addMarker(MarkerOptions().position(latLng).title(storiesLocation.name))
            boundsBuilder.include(latLng)
        }

        val bounds: LatLngBounds = boundsBuilder.build()
        googleMap.animateCamera(
            CameraUpdateFactory.newLatLngBounds(
                bounds,
                context.resources.displayMetrics.widthPixels,
                context.resources.displayMetrics.heightPixels,
                360
            )
        )

    }

}