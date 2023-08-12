package com.example.jetlearningrecorder.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.os.Environment
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.jetlearningrecorder.data.audioRecorder.AndroidAudioRecorder
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

import java.io.File
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(

) : ViewModel() {

    private val _recordingState = MutableStateFlow(RecordingState.STOPPED)
    val recordingState: StateFlow<RecordingState>
        get() = _recordingState.asStateFlow()

    private var androidAudioRecorder: AndroidAudioRecorder? = null

    fun startRecording(context: Context) {
        androidAudioRecorder = AndroidAudioRecorder(context = context)
        androidAudioRecorder?.startRecording()
        _recordingState.value = RecordingState.STARTED
    }

    fun stoppedRecording() {
        androidAudioRecorder?.stopRecording()
        _recordingState.value = RecordingState.STOPPED
    }

}

enum class RecordingState {
    STARTED,
    STOPPED
}

