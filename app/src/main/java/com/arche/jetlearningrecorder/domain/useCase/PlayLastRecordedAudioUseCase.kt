package com.arche.jetlearningrecorder.domain.useCase


import com.arche.jetlearningrecorder.data.audioPlayer.AndroidAudioPlayer
import com.arche.jetlearningrecorder.domain.repository.AudioFileRepository
import javax.inject.Inject

class PlayLastRecordedAudioUseCase @Inject constructor(
    private val repository: AudioFileRepository,
    private val audioPlayer: AndroidAudioPlayer
) {

    suspend operator fun invoke() {
        val lastRecordedAudio = repository.getLastInsertedAudioFile()
        lastRecordedAudio?.let { audioFile ->
            audioPlayer.playAudio(audioFile.filePath)
        }
    }


}