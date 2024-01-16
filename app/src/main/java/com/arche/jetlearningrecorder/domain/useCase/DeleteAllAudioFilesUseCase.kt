package com.arche.jetlearningrecorder.domain.useCase

import com.arche.jetlearningrecorder.data.audioRecorder.AndroidAudioRecorder
import com.arche.jetlearningrecorder.domain.repository.AudioFileRepository
import javax.inject.Inject

class DeleteAllAudioFilesUseCase @Inject constructor(
    private val repository: AudioFileRepository,
    private val androidAudioRecorder: AndroidAudioRecorder
) {

    suspend operator fun invoke(){
        repository.deleteAllAudioFiles()
        androidAudioRecorder.cleanCacheDirectory()
    }
}