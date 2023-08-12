package com.example.jetlearningrecorder.domain.useCase

import com.example.jetlearningrecorder.domain.model.AudioFile
import com.example.jetlearningrecorder.domain.repository.AudioFileRepository
import kotlinx.coroutines.flow.Flow

class GetAudioFilesUseCase(
    private val repository: AudioFileRepository
) {
    operator fun  invoke (): Flow<List<AudioFile>>{
        return repository.getAudioFiles()
    }
}