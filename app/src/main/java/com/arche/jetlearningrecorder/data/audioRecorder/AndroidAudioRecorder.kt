package com.arche.jetlearningrecorder.data.audioRecorder

import android.content.Context
import android.media.MediaRecorder
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class AndroidAudioRecorder(private val context: Context) {

    private var mediaRecorder: MediaRecorder? = null
    private var currentFilePath: String? = null
    private var currentFileName: String? = null


    fun startRecording(): Pair<String, String> {

        val currentDateTime =
            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        currentFileName = "jetrecord_$currentDateTime.mp3"
        currentFilePath = createOutputFilePath()

        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(currentFilePath)
            try {
                prepare()
                start()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return Pair(currentFileName!!, currentFilePath!!)
    }
    private fun createOutputFilePath(): String {
        val cacheDir = context.cacheDir
        val appName = "JetRecorder"
        val subdirectory = File(cacheDir, appName)
        //create the directory
        subdirectory.mkdirs()

        //file name
        val currentDateTime =
            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val fileName = "jetrecord_$currentDateTime.mp3"
        return File(subdirectory, fileName).absolutePath
    }

    fun cleanCacheDirectory() {
        val cacheDir = context.cacheDir
        val appName = "JetRecorder"
        val subdirectory = File(cacheDir, appName)

        subdirectory.listFiles()?.forEach { file ->
            file.delete()
        }
    }
    ///////////////////////////////////////////////////////////////////////////////

    fun stopRecording(): Pair<String, String>? {
        mediaRecorder?.apply {
            try {
                stop()
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            }
            release()
        }
        mediaRecorder = null

        return if (currentFileName != null && currentFilePath != null) {
            Pair(currentFileName!!, currentFilePath!!)
        } else {
            null
        }
    }


}