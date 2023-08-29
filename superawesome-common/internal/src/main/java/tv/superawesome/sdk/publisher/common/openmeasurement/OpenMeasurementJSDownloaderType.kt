package tv.superawesome.sdk.publisher.common.openmeasurement

/**
 * Interface for OpenMeasurement JS SDK downloader, this downloads the JS from AASDK S3 and saves
 * it in on device for use to ensure the latest OMSDK js is used as required by the IAB.
 */
interface OpenMeasurementJSDownloaderType {
    /**
     * Downloads the OMSDK JS file.
     */
    fun downloadJS()
}
