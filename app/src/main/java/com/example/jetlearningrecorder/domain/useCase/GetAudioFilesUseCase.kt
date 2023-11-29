package com.example.jetlearningrecorder.domain.useCase

import com.example.jetlearningrecorder.domain.model.AudioFile
import com.example.jetlearningrecorder.domain.repository.AudioFileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAudioFilesUseCase @Inject constructor(
    private val repository: AudioFileRepository
) {
    operator fun  invoke (): Flow<List<AudioFile>>{
        return repository.getAudioFiles()
    }
}