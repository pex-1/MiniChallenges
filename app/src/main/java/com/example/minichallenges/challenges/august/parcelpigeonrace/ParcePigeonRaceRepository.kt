package com.example.minichallenges.challenges.august.parcelpigeonrace

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import kotlin.system.measureTimeMillis

class ParcelPigeonRaceRepository {

    suspend fun getPigeonImage(pigeonImage: PigeonImage, cacheDir: File): PigeonImage? =
        withContext(Dispatchers.IO) {
            try {
                var sizeMB = 0.0
                val tempFile: File
                val elapsed = measureTimeMillis {

                    val client = OkHttpClient()
                    val url = when {
                        pigeonImage.position < 3 -> "https://picsum.photos/200?random=${System.nanoTime()}"
                        else -> "https://picsum.photos/800/800?random=${System.nanoTime()}"
                    }
                    val request = Request.Builder().url(url).build()
                    val response = client.newCall(request).execute()
                    val body = response.body

                    // Create a temp file
                    tempFile = File.createTempFile("img_", ".tmp", cacheDir)

                    // Save body into file
                    tempFile.outputStream().use { output ->
                        body.byteStream().copyTo(output)
                    }
                    // Get size in MB
                    sizeMB = tempFile.length().toDouble() / (1024 * 1024)
                }

                pigeonImage.copy(
                    durationInMilSeconds = elapsed,
                    file = tempFile,
                    size = sizeMB,
                    isLoading = false
                )
            } catch (e: Exception) {
                Log.e("ParcelPigeonRaceRepository", "error -> ${e.message} ")
                null
            }
        }
}