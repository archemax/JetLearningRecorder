package com.arche.jetlearningrecorder.data.audioPlayer

import android.content.Context
import android.media.MediaPlayer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class AndroidAudioPlayer @Inject constructor(private val context: Context) {

    private var mediaPlayer: MediaPlayer? = null

    private val _isPlaying = MutableLiveData<Boolean>()
    val isPlaying: LiveData<Boolean> get() = _isPlaying

    ////////////////////////////////////////////////////////////////////////////////////
    fun playAudio(audioFilePath: String) {
        mediaPlayer?.apply {
            stop()
            release()

        }
        mediaPlayer = MediaPlayer().apply {
            setDataSource(audioFilePath)
            prepare()
            start()
            setOnCompletionListener {
                _isPlaying.value = false

            }
            _isPlaying.value  = true

        }
    }

    fun stopAudio() {
        mediaPlayer?.apply {
            stop()
            release()
            mediaPlayer = null
            _isPlaying.value = false
        }
    }

//    fun isAudioPlaying(): Boolean{
//        return _isPlaying.value
//    }
}