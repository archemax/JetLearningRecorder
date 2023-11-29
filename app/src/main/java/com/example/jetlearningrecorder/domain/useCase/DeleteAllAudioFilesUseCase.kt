package com.example.jetlearningrecorder.domain.useCase

import com.example.jetlearningrecorder.domain.repository.AudioFileRepository
import javax.inject.Inject

class DeleteAllAudioFilesUseCase @Inject constructor(
    private val repository: AudioFileRepository
) {

    suspend operator fun invoke(){
        repository.deleteAllAudioFiles()
    }
}