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
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object AppModule {

    @Provides
    @Singleton
    fun provideContext(app: Application): Context {
        return app.applicationContext
    }


    @Provides
    @Singleton
    fun provideAudioFileDatabase(app: Application): AudioFileDatabase {
        return Room.databaseBuilder(
            app,
            AudioFileDatabase::class.java,
             AudioFileDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideAudioFileRepository(db: AudioFileDatabase): AudioFileRepository{
        return AudioFileRepositoryImpl(db.audioFileDao)
    }

    //use case
    @Provides
    @Singleton
    fun provideInsertAudioFileUseCase(repository: AudioFileRepository): InsertAudioFileUseCase{
        return InsertAudioFileUseCase(repository)
    }
}