package com.example.dicodingsubmissionintermediate_akmalrafinursyabani.preference

import android.content.Context
import com.example.dicodingsubmissionintermediate_akmalrafinursyabani.model.User

class UserPreference(context: Context) {

    private val preference = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    fun setSession(user: User) {
        val editor = preference.edit()
        editor.putString(ID, user.id)
        editor.putString(NAME, user.name)
        editor.putString(EMAIL, user.email)
        editor.putString(TOKEN, user.token)
        editor.apply()
    }

    fun getSession(): User {
        val model = User()
        model.id = preference.getString(ID, "")
        model.name = preference.getString(NAME, "")
        model.email = preference.getString(EMAIL, "")
        model.token = preference.getString(TOKEN, "")

        return model
    }

    fun clearSession() {
        val editor = preference.edit()
        editor.clear()
        editor.apply()
    }

    companion object {
        private const val PREFS = "mydicoding_submission_session_pref"
        private const val ID = "id"
        private const val NAME = "name"
        private const val EMAIL = "email"
        private const val TOKEN = "token"
    }
}