package com.arche.jetlearningrecorder.presentation

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arche.jetlearningrecorder.data.audioPlayer.AndroidAudioPlayer
import com.arche.jetlearningrecorder.data.audioRecorder.AndroidAudioRecorder
import com.arche.jetlearningrecorder.domain.model.AudioFile
import com.arche.jetlearningrecorder.domain.useCase.InsertAudioFileUseCase
import com.arche.jetlearningrecorder.domain.useCase.PlayLastRecordedAudioUseCase

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Timer
import javax.inject.Inject
import kotlin.concurrent.fixedRateTimer
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime


@HiltViewModel
@ExperimentalTime
class MainViewModel @Inject constructor(
    private val insertAudioFileUseCase: InsertAudioFileUseCase,
    private val playLastRecordedAudioUseCase: PlayLastRecordedAudioUseCase,
    private val audioPlayer: AndroidAudioPlayer
) : ViewModel() {

    private val _isPlayingLastRecordedAudio = MutableLiveData<Boolean>()
    val isPlayingLastRecordedAudio: LiveData<Boolean> = _isPlayingLastRecordedAudio

    init {
        audioPlayer.isPlaying.observeForever { playingFromPlayer ->
            _isPlayingLastRecordedAudio.value = playingFromPlayer
            Log.d("ViewModel_isPlayingLastRecordedAudio", "${_isPlayingLastRecordedAudio.value}")
        }
    }


    private var time: Duration = Duration.ZERO
    private lateinit var timer: Timer

    var seconds by mutableStateOf("00")
    var minutes by mutableStateOf("00")
    var hours by mutableStateOf("00")

    var isPlaying by mutableStateOf(false)


    fun startTimer() {
        timer = fixedRateTimer(initialDelay = 1000L, period = 1000L) {
            time = time.plus(1.seconds)
            updateTimeStates()
        }
        isPlaying = true
    }

    private fun updateTimeStates() {
        time.toComponents { hours, minutes, seconds, _ ->
            this@MainViewModel.seconds = seconds.pad()
            this@MainViewModel.minutes = minutes.pad()
            this@MainViewModel.hours = hours.pad()
        }
    }

    private fun Int.pad(): String {
        return this.toString().padStart(2, '0')
    }

    private fun Long.pad(): String {
        return this.toString().padStart(2, '0')
    }

    fun stopTimer() {
        timer.cancel()
        isPlaying = false
        time = Duration.ZERO
        updateTimeStates()
    }

    private var currentFileName: String? = null
    private var currentFilePath: String? = null

    private var androidAudioRecorder: AndroidAudioRecorder? = null
    private var isRecording = false


    fun startRecording(context: Context) {

        androidAudioRecorder = AndroidAudioRecorder(context = context)
        val (fileName, filePath) = androidAudioRecorder?.startRecording() ?: return
        currentFileName = fileName
        currentFilePath = filePath

        isRecording = true
        Log.d("AAA", "recordig stated file name = $currentFileName ; filePath = $currentFilePath")
    }

    fun stoppedRecording() {
        androidAudioRecorder?.stopRecording()?.let { (fileName, filePath) ->
            currentFileName = fileName
            currentFilePath = filePath
            isRecording = false
        }
    }

    fun insertAudioFileToDb() {
        if (currentFileName != null && currentFilePath != null) {
            val audioFile = AudioFile(filePath = currentFilePath!!, title = currentFileName!!)
            viewModelScope.launch {
                insertAudioFileUseCase(audioFile)
                Log.d(
                    "AAA",
                    "FILE INSERTED file name = $currentFileName ; filePath = $currentFilePath"
                )
            }
        }
    }

    fun playLastRecordedAudioFile() {
        viewModelScope.launch {
            playLastRecordedAudioUseCase()
        }
    }
}








