package com.arche.jetlearningrecorder.domain.useCase

import com.arche.jetlearningrecorder.domain.model.AudioFile
import com.arche.jetlearningrecorder.domain.repository.AudioFileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAudioFilesUseCase @Inject constructor(
    private val repository: AudioFileRepository
) {
    operator fun  invoke (): Flow<List<AudioFile>>{
        return repository.getAudioFiles()
    }
}