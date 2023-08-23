package com.example.jetlearningrecorder.data.audioPlayer

import android.content.Context
import android.media.MediaPlayer
import javax.inject.Inject



class AndroidAudioPlayer @Inject constructor (private val context: Context){

    private var mediaPlayer: MediaPlayer? = null

    fun playAudio (audioFilePath: String){
        mediaPlayer?.apply {
            stop()
            release()

        }
        mediaPlayer = MediaPlayer().apply {
            setDataSource(audioFilePath)
            prepare()
            start()
        }
    }


    fun stopAudio (){
        mediaPlayer?.apply {
            stop()
            release()
            mediaPlayer = null
        }
    }
}