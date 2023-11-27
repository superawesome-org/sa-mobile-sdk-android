package tv.superawesome.sdk.publisher.testutil

import android.content.SharedPreferences

class InMemoryPreferences : SharedPreferences {
    val data = mutableMapOf<String, Any?>()
    override fun getAll(): MutableMap<String, *> = data

    override fun getString(key: String?, defValue: String?): String? =
        data[key] as? String ?: defValue

    override fun getStringSet(key: String?, defValues: MutableSet<String>?): MutableSet<String>? {
        TODO("Not yet implemented")
    }

    override fun getInt(key: String?, defValue: Int): Int {
        TODO("Not yet implemented")
    }

    override fun getLong(key: String?, defValue: Long): Long {
        TODO("Not yet implemented")
    }

    override fun getFloat(key: String?, defValue: Float): Float {
        TODO("Not yet implemented")
    }

    override fun getBoolean(key: String?, defValue: Boolean): Boolean {
        TODO("Not yet implemented")
    }

    override fun contains(key: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun edit(): SharedPreferences.Editor {
        return Editor(data)
    }

    override fun registerOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener?) {
        TODO("Not yet implemented")
    }

    override fun unregisterOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener?) {
        TODO("Not yet implemented")
    }

    private class Editor(val data: MutableMap<String, Any?>) : SharedPreferences.Editor {
        val actions = mutableListOf<() -> Unit>()
        override fun putString(key: String?, value: String?): SharedPreferences.Editor {
            if (key != null) {
                actions.add { data[key] = value }
            }
            return this
        }

        override fun putStringSet(
            key: String?,
            values: MutableSet<String>?,
        ): SharedPreferences.Editor {
            TODO("Not yet implemented")
        }

        override fun putInt(key: String?, value: Int): SharedPreferences.Editor {
            TODO("Not yet implemented")
        }

        override fun putLong(key: String?, value: Long): SharedPreferences.Editor {
            TODO("Not yet implemented")
        }

        override fun putFloat(key: String?, value: Float): SharedPreferences.Editor {
            TODO("Not yet implemented")
        }

        override fun putBoolean(key: String?, value: Boolean): SharedPreferences.Editor {
            TODO("Not yet implemented")
        }

        override fun remove(key: String?): SharedPreferences.Editor {
            actions.add {
                if (key != null) data.remove(key)
            }
            return this
        }

        override fun clear(): SharedPreferences.Editor {
            data.clear()
            return this
        }

        override fun commit(): Boolean {
            TODO("Not yet implemented")
        }

        override fun apply() {
            actions.forEach { action ->
                action()
            }
        }
    }
}
