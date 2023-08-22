package com.example.jetlearningrecorder.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.jetlearningrecorder.data.data_source.AudioFileDatabase
import com.example.jetlearningrecorder.data.repository.AudioFileRepositoryImpl
import com.example.jetlearningrecorder.domain.repository.AudioFileRepository

import com.example.jetlearningrecorder.domain.useCase.InsertAudioFileUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object AppModule {

    @Provides
    @Singleton
    fun provideAudioFileDatabase(
        @ApplicationContext context: Context
    ): AudioFileDatabase {
        return Room.databaseBuilder(
            context,
            AudioFileDatabase::class.java,
            AudioFileDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideAudioFileDao(
        audioFileDatabase: AudioFileDatabase
    ) = audioFileDatabase.audioFileDao

    @Provides
    @Singleton
    fun provideAudioFileRepository(db: AudioFileDatabase): AudioFileRepository {
        return AudioFileRepositoryImpl(db.audioFileDao)
    }

    //use case
    @Provides
    @Singleton
    fun provideInsertAudioFileUseCase(repository: AudioFileRepository): InsertAudioFileUseCase {
        return InsertAudioFileUseCase(repository)
    }
}