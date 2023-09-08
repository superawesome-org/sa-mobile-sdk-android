package tv.superawesome.sdk.publisher.common.repositories

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

interface PreferencesRepositoryType {
    var userAgent: String?
    var dauUniquePart: String?
}

private object Keys {
    const val preferencesFileName = "SuperAwesome.preferences"
    const val userAgent = "userAgent"
    const val dauUniquePart = "dauUniquePart"
}

@Suppress("UseDataClass")
class PreferencesRepository(private val context: Context) : PreferencesRepositoryType {
    override var userAgent: String?
        get() = preferences.getString(Keys.userAgent, null)
        set(value) {
            preferences.edit().putString(Keys.userAgent, value).apply()
        }

    override var dauUniquePart: String?
        get() = preferences.getString(Keys.dauUniquePart, null)
        set(value) {
            preferences.edit().putString(Keys.dauUniquePart, value).apply()
        }

    private val preferences: SharedPreferences
        get() = context.getSharedPreferences(Keys.preferencesFileName, MODE_PRIVATE)
}
