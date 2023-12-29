package com.example.jetlearningrecorder.domain.useCase

import com.example.jetlearningrecorder.data.audioPlayer.AndroidAudioPlayer
import com.example.jetlearningrecorder.domain.repository.AudioFileRepository

class PlayAudioFileUseCase(
    val repository: AudioFileRepository,
    val audioPlayer: AndroidAudioPlayer
) {
    suspend operator fun invoke(audioFileId: Int) {
        val selectedAudioFile = repository.playAudioFileById(audioFileId)

        selectedAudioFile?.let { file ->
            audioPlayer.playAudio(file.filePath)
        }
    }
}