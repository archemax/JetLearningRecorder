package com.example.jetlearningrecorder.data.repository

import com.example.jetlearningrecorder.data.data_source.AudioFileDao
import com.example.jetlearningrecorder.domain.model.AudioFile
import com.example.jetlearningrecorder.domain.repository.AudioFileRepository
import kotlinx.coroutines.flow.Flow

class AudioFileRepositoryImpl(
    private val dao: AudioFileDao
): AudioFileRepository {
    override fun getAudioFiles(): Flow<List<AudioFile>> {
        return dao.getFiles()
    }

    override suspend fun getAudioFileById(id: Int): AudioFile? {
        return dao.getAudioFileById(id)
    }

    override suspend fun insertAudioFile(audioFile: AudioFile) {
        dao.insertAudioFile(audioFile)
    }

    override suspend fun deleteAudioFile(audioFile: AudioFile) {
        dao.deleteNote(audioFile)
    }
}