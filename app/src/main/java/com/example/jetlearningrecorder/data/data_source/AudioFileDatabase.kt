package com.example.jetlearningrecorder.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jetlearningrecorder.domain.model.AudioFile


@Database(
    entities = [AudioFile::class],
    version = 1,
    exportSchema = false
)
abstract class AudioFileDatabase : RoomDatabase() {

    abstract val audioFileDao: AudioFileDao

    companion object {
        const val DATABASE_NAME = "audioFile"
    }
}

