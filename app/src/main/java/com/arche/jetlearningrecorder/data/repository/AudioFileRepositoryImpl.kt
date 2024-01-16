package com.arche.jetlearningrecorder.data.repository


import com.arche.jetlearningrecorder.data.data_source.AudioFileDao
import com.arche.jetlearningrecorder.domain.model.AudioFile
import com.arche.jetlearningrecorder.domain.repository.AudioFileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AudioFileRepositoryImpl @Inject constructor(
    private val dao: AudioFileDao
): AudioFileRepository {
    override fun getAudioFiles(): Flow<List<AudioFile>> {
        return dao.getFiles()
    }

    override suspend fun playAudioFileById(id: Int): AudioFile? {
        return dao.getAudioFileById(id)
    }

    override suspend fun insertAudioFile(audioFile: AudioFile) {
        dao.insertAudioFile(audioFile)
    }

    override suspend fun deleteAudioFile(audioFile: AudioFile) {
        dao.deleteAudioFile(audioFile)
    }

    override suspend fun getLastInsertedAudioFile(): AudioFile? {
        return dao.getLastInsertedAudioFile()
    }

    override suspend fun deleteAllAudioFiles() {
        dao.deleteAllAudioFiles()
    }
}