package com.example.jetlearningrecorder.domain.useCase

import com.example.jetlearningrecorder.domain.model.AudioFile
import com.example.jetlearningrecorder.domain.repository.AudioFileRepository

class InsertAudioFileUseCase(
    private val repository: AudioFileRepository
) {
    suspend operator fun invoke (audioFile:AudioFile ){
        repository.insertAudioFile(audioFile)
    }
}