package tv.superawesome.sdk.publisher.common.testutil

import android.content.SharedPreferences

class InMemorySharedPreferences : SharedPreferences {

    private val data = mutableMapOf<String, Any?>()
    private val editor = Editor(data)
    private val listeners = mutableSetOf<SharedPreferences.OnSharedPreferenceChangeListener>()

    override fun getAll(): MutableMap<String, *> = data

    override fun getString(key: String?, defValue: String?): String? {
        key ?: return null
        return data[key] as? String ?: defValue
    }

    @Suppress("UNCHECKED_CAST")
    override fun getStringSet(key: String?, defValues: MutableSet<String>?): MutableSet<String>? {
        key ?: return null
        return data[key] as? MutableSet<String>
    }

    override fun getInt(key: String?, defValue: Int): Int {
        key ?: return defValue
        return data[key] as? Int ?: defValue
    }

    override fun getLong(key: String?, defValue: Long): Long {
        key ?: return defValue
        return data[key] as? Long ?: defValue
    }

    override fun getFloat(key: String?, defValue: Float): Float {
        key ?: return defValue
        return data[key] as? Float ?: defValue
    }

    override fun getBoolean(key: String?, defValue: Boolean): Boolean {
        key ?: return defValue
        return data[key] as? Boolean ?: defValue
    }

    override fun contains(key: String?): Boolean = data.contains(key)

    override fun edit(): SharedPreferences.Editor = editor

    override fun registerOnSharedPreferenceChangeListener(
        listener: SharedPreferences.OnSharedPreferenceChangeListener?
    ) {
        if (listener != null) {
            listeners.add(listener)
        }
    }

    override fun unregisterOnSharedPreferenceChangeListener(
        listener: SharedPreferences.OnSharedPreferenceChangeListener?
    ) {
        if (listener != null) {
            listeners.remove(listener)
        }
    }

    class Editor(val data: MutableMap<String, Any?>) : SharedPreferences.Editor {

        override fun putString(key: String?, value: String?): SharedPreferences.Editor =
            putAny(key, value)

        override fun putStringSet(
            key: String?,
            values: MutableSet<String>?,
        ): SharedPreferences.Editor = putAny(key, values)

        override fun putInt(key: String?, value: Int): SharedPreferences.Editor =
            putAny(key, value)

        override fun putLong(key: String?, value: Long): SharedPreferences.Editor =
            putAny(key, value)

        override fun putFloat(key: String?, value: Float): SharedPreferences.Editor =
            putAny(key, value)

        override fun putBoolean(key: String?, value: Boolean): SharedPreferences.Editor =
            putAny(key, value)

        override fun remove(key: String?): SharedPreferences.Editor {
            if (key != null) {
                data.remove(key)
            }
            return this
        }

        override fun clear(): SharedPreferences.Editor {
            data.clear()
            return this
        }

        override fun commit(): Boolean = true // Already commited

        override fun apply() {
            // Do nothing
        }

        private fun putAny(key: String?, value: Any?): SharedPreferences.Editor {
            if (key != null) {
                data[key] = value
            }

            return this
        }
    }
}
