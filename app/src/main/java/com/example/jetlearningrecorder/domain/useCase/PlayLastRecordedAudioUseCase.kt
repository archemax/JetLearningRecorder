package com.example.jetlearningrecorder.domain.useCase

import com.example.jetlearningrecorder.data.audioPlayer.AndroidAudioPlayer
import com.example.jetlearningrecorder.domain.repository.AudioFileRepository
import javax.inject.Inject

class PlayLastRecordedAudioUseCase @Inject constructor(
    private val repository: AudioFileRepository,
    private val audioPlayer: AndroidAudioPlayer
) {

    suspend operator fun invoke (){
        val lastRecordedAudio = repository.getLastInsertedAudioFile()
        lastRecordedAudio?.let{audioFile ->
            audioPlayer.playAudio(audioFile.filePath)
        }
    }


}