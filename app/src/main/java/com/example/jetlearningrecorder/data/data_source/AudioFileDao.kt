package com.example.jetlearningrecorder.data.data_source


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jetlearningrecorder.domain.model.AudioFile
import kotlinx.coroutines.flow.Flow

@Dao
interface AudioFileDao {

    @Query("SELECT * FROM audioFile")
    fun getFiles (): Flow<List<AudioFile>>

    @Query ("SELECT * FROM audioFile WHERE id = :id")
    suspend fun getAudioFileById(id: Int): AudioFile?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAudioFile(audioFile: AudioFile)

    @Delete
    suspend fun deleteAudioFile(audioFile: AudioFile)

    @Query ("DELETE FROM audioFile")
    suspend fun deleteAllAudioFiles ()


    @Query ("SELECT * FROM audioFile ORDER BY id DESC LIMIT 1")
    suspend fun getLastInsertedAudioFile(): AudioFile?

}