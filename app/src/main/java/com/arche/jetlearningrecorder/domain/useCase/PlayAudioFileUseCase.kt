package com.arche.jetlearningrecorder.domain.useCase

import com.arche.jetlearningrecorder.data.audioPlayer.AndroidAudioPlayer
import com.arche.jetlearningrecorder.domain.repository.AudioFileRepository


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