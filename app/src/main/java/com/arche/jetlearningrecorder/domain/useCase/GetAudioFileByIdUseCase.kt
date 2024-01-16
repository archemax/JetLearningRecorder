package com.arche.jetlearningrecorder.domain.useCase

import com.arche.jetlearningrecorder.domain.repository.AudioFileRepository

class GetAudioFileById(
    private val repository: AudioFileRepository
) {
    suspend operator fun invoke (id: Int){
        repository.playAudioFileById(id)
    }
}