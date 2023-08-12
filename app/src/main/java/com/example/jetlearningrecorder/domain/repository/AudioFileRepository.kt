package com.example.jetlearningrecorder.domain.repository

import com.example.jetlearningrecorder.domain.model.AudioFile
import kotlinx.coroutines.flow.Flow

interface AudioFileRepository {

    fun getAudioFiles(): Flow<List<AudioFile>>
    suspend fun getAudioFileById(id: Int): AudioFile?
    suspend fun insertAudioFile(audioFile: AudioFile)
    suspend fun deleteAudioFile(audioFile: AudioFile)
}