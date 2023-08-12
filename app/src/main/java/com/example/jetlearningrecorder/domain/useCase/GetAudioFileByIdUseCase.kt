package com.example.jetlearningrecorder.domain.useCase

import com.example.jetlearningrecorder.domain.model.AudioFile
import com.example.jetlearningrecorder.domain.repository.AudioFileRepository

class GetAudioFileById(
    private val repository: AudioFileRepository
) {
    suspend operator fun invoke (id: Int){
        repository.getAudioFileById(id)
    }
}