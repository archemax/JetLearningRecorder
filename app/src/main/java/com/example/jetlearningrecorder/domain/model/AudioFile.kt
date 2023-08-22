package com.example.jetlearningrecorder.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "audioFile")
data class AudioFile(
    val filePath: String,
    val title: String,

    @PrimaryKey (autoGenerate = true)
    val id: Int? = null
)