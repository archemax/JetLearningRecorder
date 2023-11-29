package com.example.jetlearningrecorder.presentation.RecordsListScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetlearningrecorder.domain.model.AudioFile
import com.example.jetlearningrecorder.domain.useCase.DeleteAllAudioFilesUseCase
import com.example.jetlearningrecorder.domain.useCase.GetAudioFilesUseCase
import com.example.jetlearningrecorder.domain.useCase.PlayAudioFileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class RecordsListScreenViewModel @Inject constructor(
    val getAudioFilesUseCase: GetAudioFilesUseCase,
    val playAudioFileUseCase: PlayAudioFileUseCase,
    private val deleteAllAudioFileUseCase: DeleteAllAudioFilesUseCase
): ViewModel() {

    //use StateFlow to get files
    private val _audioFilesState = MutableStateFlow<List<AudioFile>>(emptyList())
    val audioFilesState:StateFlow<List<AudioFile>> = _audioFilesState

    fun getAudioFiles(){
        viewModelScope.launch {
            _audioFilesState.value = getAudioFilesUseCase().first()
        }
    }

    // play audio file
    fun playSelectedAudioFile(audioFileId: Int){
        viewModelScope.launch {
            playAudioFileUseCase(audioFileId)
        }
    }

    fun deleteAllAudioFiles(){
        viewModelScope.launch {
            deleteAllAudioFileUseCase.invoke()
            // and update the list of files
            _audioFilesState.value = getAudioFilesUseCase().first()
        }
    }

}