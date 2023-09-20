package tv.superawesome.demoapp.caching

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

private object Keys {
    const val preferencesFileName = "preferences"
    const val userPlacements = "userPlacements"
}

@Suppress("UseDataClass")
class UserPlacementsCache(private val context: Context) {
    var userPlacements: String
        get() = preferences.getString(Keys.userPlacements, null) ?: "[]"
        set(value) {
            preferences.edit().putString(Keys.userPlacements, value).apply()
        }

    private val preferences: SharedPreferences
        get() = context.getSharedPreferences(Keys.preferencesFileName, MODE_PRIVATE)
}
