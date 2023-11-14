package tv.superawesome.sdk.publisher.components

import android.content.SharedPreferences
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import tv.superawesome.sdk.publisher.network.datasources.NetworkDataSourceType
import java.io.File

/**
 * Provides caching for videos.
 */
interface VideoCache {

    /**
     * Gets a video in the cache, if it doesn't exist, it will be downloaded.
     *
     * @return the path of the video file.
     * @throws Throwable if file cannot be downloaded.
     */
    suspend fun get(url: String): String

    /**
     * Cleans up the cache, following the cache expiration policy.
     */
    fun cleanUp()
}

/**
 * Policies for caching video.
 */
sealed class VideoCachePolicy {
    /**
     * Duration of the cache.
     */
    abstract val duration: Long

    /**
     * No caching, all videos will be deleted.
     */
    data object NoCaching : VideoCachePolicy() {
        override val duration: Long = 0L
    }

    /**
     * Give a max age for the videos.
     *
     * @property duration duration of the cache.
     */
    data class MaxAge(override val duration: Long) : VideoCachePolicy()

    companion object {
        private const val CACHE_DURATION = 1_000 * 60 * 60 * 24 * 7L // 1 Week

        /**
         * Default cache policy is max age of 1 week.
         */
        val DefaultCachePolicy = MaxAge(CACHE_DURATION)
    }
}

/**
 * Default implementation of [VideoCache].
 */
class VideoCacheImpl(
    private val preferences: SharedPreferences,
    private val remoteDataSource: NetworkDataSourceType,
    private val logger: Logger,
    private val timeProvider: TimeProviderType,
    private val policy: VideoCachePolicy = VideoCachePolicy.DefaultCachePolicy,
) : VideoCache {

    init {
        cleanUp()
    }

    override suspend fun get(url: String): String =
        preferences.getString(url, null)?.let { entry ->
            Json.decodeFromString<CacheEntry>(entry).path
        } ?: cache(url)

    private suspend fun cache(url: String): String {
        remoteDataSource.downloadFile(url).fold(
            onSuccess = { filePath ->
                if (policy != VideoCachePolicy.NoCaching) {
                    val entry = CacheEntry(filePath, timeProvider.millis())
                    preferences.edit()
                        .putString(url, Json.encodeToString(entry))
                        .apply()
                }

                return filePath
            },
            onFailure = { t ->
                logger.error("Error downloading file", t)
                throw t
            }
        )
    }

    override fun cleanUp() {
        val now = timeProvider.millis()
        val expired = preferences
            .all
            .entries
            .map { (key, value) -> key to Json.decodeFromString<CacheEntry>(value as String) }
            .filter { (_, entry) -> now - entry.timestamp > policy.duration }

        val editor = preferences.edit()
        expired.forEach { (key, entry) ->
            editor.remove(key)
            try {
                val file = File(entry.path)
                if (file.exists()) {
                    file.delete()
                }
            } catch (e: SecurityException) {
                logger.error("File ${entry.path} couldn't be deleted", e)
            }
        }
        editor.apply()
    }

    @Serializable
    private data class CacheEntry(
        val path: String,
        val timestamp: Long,
    )
}
