package com.example.jetlearningrecorder.data.audioRecorder

import android.content.Context
import android.media.MediaRecorder
import android.os.Environment

import java.io.File
import java.io.IOException


class AndroidAudioRecorder(private val context: Context) {

    private var mediaRecorder: MediaRecorder? = null

    fun startRecording() {

        val outputFilePath = createOutputFilePath()

        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(outputFilePath)
            try {
                prepare()
                start()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun createOutputFilePath(): String {
        val cacheDir = context.cacheDir
        val appName = "JetRecorder"
        val subdirectory = File(cacheDir, appName)
        //create the directory
        subdirectory.mkdirs()

        val fileName = "jetrecord_${System.currentTimeMillis()}.mp3"
        return File(subdirectory, fileName).absolutePath
    }

    fun stopRecording() {
        mediaRecorder?.apply {
            try {
                stop()
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            }
            release()
        }
        mediaRecorder = null
    }
}