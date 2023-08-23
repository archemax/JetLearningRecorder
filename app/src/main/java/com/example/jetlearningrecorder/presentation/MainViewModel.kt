package com.example.jetlearningrecorder.presentation

import android.content.Context
import android.util.Log
import android.util.LogPrinter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetlearningrecorder.data.audioPlayer.AndroidAudioPlayer
import com.example.jetlearningrecorder.data.audioRecorder.AndroidAudioRecorder
import com.example.jetlearningrecorder.domain.model.AudioFile
import com.example.jetlearningrecorder.domain.repository.AudioFileRepository
import com.example.jetlearningrecorder.domain.useCase.InsertAudioFileUseCase
import com.example.jetlearningrecorder.domain.useCase.PlayLastRecordedAudioUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val insertAudioFileUseCase: InsertAudioFileUseCase,
    private val playLastRecordedAudioUseCase: PlayLastRecordedAudioUseCase,
    private val audioPlayer: AndroidAudioPlayer
) : ViewModel() {


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

    fun playLastRecordedAudioFile (){
        viewModelScope.launch {
            playLastRecordedAudioUseCase()

        }
    }
}



