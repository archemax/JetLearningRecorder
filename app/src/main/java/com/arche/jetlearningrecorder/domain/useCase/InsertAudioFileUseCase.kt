package com.arche.jetlearningrecorder.domain.useCase


import com.arche.jetlearningrecorder.domain.model.AudioFile
import com.arche.jetlearningrecorder.domain.repository.AudioFileRepository
import javax.inject.Inject

class InsertAudioFileUseCase @Inject constructor(
    private val repository: AudioFileRepository
) {
    suspend operator fun invoke (audioFile: AudioFile){
        repository.insertAudioFile(audioFile)
    }
}