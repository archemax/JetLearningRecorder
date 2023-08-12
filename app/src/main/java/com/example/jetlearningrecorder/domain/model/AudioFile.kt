package com.example.jetlearningrecorder.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AudioFile(
    val filePath: String,
    val title: String,

    @PrimaryKey val id: Int? = null
) {
}