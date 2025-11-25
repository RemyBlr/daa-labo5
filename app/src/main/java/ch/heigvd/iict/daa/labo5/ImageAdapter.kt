package ch.heigvd.iict.daa.labo5

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.URL

/**
 * Adapter for displaying images in a RecyclerView with caching.
 * Handles downloading and caching with coroutines.
 *
 * @param imageIds List of image IDs to display.
 * @param cacheDir Directory to use for caching images.
 * @param scope CoroutineScope for launching coroutines.
 */
class ImageAdapter(
    private val imageIds: List<Int>,
    private val cacheDir: File,
    private val scope: CoroutineScope,
)
    : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    /**
     * Companion object holding constants.
     */
    companion object {
        private const val BASE_URL = "https://daa.iict.ch/images/"
        private const val CACHE_VALIDITY_MINUTES = 5L
    }

    /**
     * ViewHolder for image items.
     * Contains an ImageView and a ProgressBar.
     * Job reference to manage coroutine loading.
     */
    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image_view)
        val progressBar: ProgressBar = itemView.findViewById(R.id.image_progress_bar)
        var job: Job? = null
    }

    /**
     * Creates a new ViewHolder for an image item.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_item, parent, false)
        return ImageViewHolder(view)
    }

    /**
     * Binds data to the ViewHolder at the specified position.
     * Handles image loading with caching and coroutine management.
     */
    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageId = imageIds[position]

        // Cancel any previous job if exists foor this holder
        holder.job?.cancel()

        holder.progressBar.visibility = View.VISIBLE
        holder.imageView.setImageDrawable(null)

        // New coroutine job to load the image
        holder.job = scope.launch {
            try {

                val cacheFile = getCacheFile(imageId)

                // decide whether to load from cache or download
                val bitmap = if (isCacheValid(cacheFile)) {
                    loadFromCache(cacheFile)
                } else {
                    downloadAndCache(imageId, cacheFile)
                }

                holder.imageView.setImageBitmap(bitmap)
                holder.progressBar.visibility = View.GONE
            } catch (e: Exception) {
                e.printStackTrace()
                holder.progressBar.visibility = View.GONE
            }
        }
    }

    /**
     * Returns the total number of items.
     */
    override fun getItemCount(): Int = imageIds.size

    /**
     * Called when a ViewHolder is recycled.
     * Cancels any ongoing job and clears the ImageView.
     */
    override fun onViewRecycled(holder: ImageViewHolder) {
        super.onViewRecycled(holder)
        // Cancel any ongoing job when the view is recycled
        holder.job?.cancel()
        holder.imageView.setImageDrawable(null)
    }

    /**
     * Gets the cache file for a given image ID.
     * @param imageId The ID of the image.
     * @return The File object representing the cache file.
     */
    private fun getCacheFile(imageId: Int): File {
        return File(cacheDir, "image_$imageId.jpg")
    }

    /**
     * Checks if the cache file is valid based on its age.
     * @param cacheFile The cache file to check.
     * @return True if the cache is valid, false otherwise.
     */
    private fun isCacheValid(cacheFile: File): Boolean {
        if (!cacheFile.exists()) { return false }

        // Check age of the file
        val currentTime = System.currentTimeMillis()
        val fileTime = cacheFile.lastModified()
        val ageInMinutes = (currentTime - fileTime) / (1000 * 60)

        return ageInMinutes < CACHE_VALIDITY_MINUTES
    }

    /**
     * Loads a bitmap from the cache file.
     * Dispatches IO for file reading and Default for decoding.
     * @param cacheFile The cache file to load from.
     * @return The loaded Bitmap.
     */
    private suspend fun loadFromCache(cacheFile: File): Bitmap = withContext(Dispatchers.IO) {
        // Read the file bytes from cache
        val bytes = cacheFile.readBytes()

        // decode the bitmap
        withContext(Dispatchers.Default) {
            BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                ?: throw Exception("Can't decode cached image")
        }
    }

    /**
     * Downloads an image, saves it to cache, and decodes it.
     * @param imageId The ID of the image to download.
     * @param cacheFile The cache file to save the image to.
     * @return The downloaded and decoded Bitmap.
     */
    private suspend fun downloadAndCache(imageId: Int, cacheFile: File): Bitmap {
        val imageBytes = downloadImage(imageId) // Download image
        saveToCache(imageBytes, cacheFile)
        return decodeImage(imageBytes)
    }

    /**
     * Downloads image bytes from the given image ID.
     * Dispatches IO for network operation.
     * @param imageId The ID of the image to download.
     * @return The downloaded image bytes.
     */
    private suspend fun downloadImage(imageId: Int): ByteArray = withContext(Dispatchers.IO) {
        val url = URL("$BASE_URL$imageId.jpg")
        url.openStream().use { input ->
            input.readBytes()
        }
    }

    /**
     * Saves image bytes to the cache file.
     * Dispatches IO for file writing.
     * @param imageBytes The image bytes to save.
     * @param cacheFile The cache file to write to.
     */
    private suspend fun saveToCache(imageBytes: ByteArray, cacheFile: File) = withContext(Dispatchers.IO) {
        FileOutputStream(cacheFile).use { output ->
            output.write(imageBytes)
        }
    }

    /**
     * Decodes image bytes into a Bitmap.
     * Dispatches Default for decoding operation.
     * @param imageBytes The image bytes to decode.
     * @return The decoded Bitmap.
     */
    private suspend fun decodeImage(imageBytes: ByteArray): Bitmap = withContext(Dispatchers.Default) {
        BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            ?: throw Exception("Can't decode downloaded image")
    }
}