package com.dza.komikara

import android.content.Context
import android.content.SharedPreferences

class PrefManager private constructor(context: Context) {
    private val sharedPreferences: SharedPreferences
    companion object {
        private const val PREFS_FILENAME = "AuthAppPrefs"
        private const val KEY_IS_LOGGED_IN = "isLoggedIn"
        private const val KEY_IS_ADMIN = "isAdmin"
        private const val KEY_EMAIL = "email"
        private const val KEY_PASSWORD = "password"
        @Volatile
        private var instance: PrefManager? = null
        fun getInstance(context: Context): PrefManager {
            return instance ?: synchronized(this) {
                instance ?: PrefManager(context.applicationContext).also {
                    instance = it
                }
            }
        }
    }
    init {
        sharedPreferences = context.getSharedPreferences(
            PREFS_FILENAME,
            Context.MODE_PRIVATE)
    }
    fun setLoggedIn(isLoggedIn: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn)
        editor.apply()
    }
    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }
    fun setAdmin(isAdmin: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(KEY_IS_ADMIN, isAdmin)
        editor.apply()
    }
    fun isAdmin(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_ADMIN, false)
    }
    fun saveEmail(email: String) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_EMAIL, email)
        editor.apply()
    }
    fun savePassword(password: String) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_PASSWORD, password)
        editor.apply()
    }
    fun saveUsername(username: String) {
        val editor = sharedPreferences.edit()
        editor.putString("username", username)
        editor.apply()
    }
    fun getEmail(): String {
        return sharedPreferences.getString(KEY_EMAIL, "") ?: ""
    }
    fun getPassword(): String {
        return sharedPreferences.getString(KEY_PASSWORD, "") ?: ""
    }
    fun getUsername(): String {
        return sharedPreferences.getString("username", "") ?: ""
    }
    fun clear() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}