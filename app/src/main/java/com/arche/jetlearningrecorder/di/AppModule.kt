package com.arche.jetlearningrecorder.di

import android.content.Context
import androidx.room.Room
import com.arche.jetlearningrecorder.data.audioPlayer.AndroidAudioPlayer
import com.arche.jetlearningrecorder.data.audioRecorder.AndroidAudioRecorder
import com.arche.jetlearningrecorder.data.data_source.AudioFileDatabase
import com.arche.jetlearningrecorder.data.repository.AudioFileRepositoryImpl
import com.arche.jetlearningrecorder.domain.repository.AudioFileRepository
import com.arche.jetlearningrecorder.domain.useCase.DeleteAllAudioFilesUseCase
import com.arche.jetlearningrecorder.domain.useCase.GetAudioFilesUseCase
import com.arche.jetlearningrecorder.domain.useCase.InsertAudioFileUseCase
import com.arche.jetlearningrecorder.domain.useCase.PlayAudioFileUseCase
import com.arche.jetlearningrecorder.domain.useCase.PlayLastRecordedAudioUseCase

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

    // ---- use cases -----//////////////////////////////////////////////////////////////
    @Provides
    @Singleton
    fun provideInsertAudioFileUseCase(repository: AudioFileRepository): InsertAudioFileUseCase {
        return InsertAudioFileUseCase(repository)
    }

    @Provides
    @Singleton
    fun providePlayAudioFileUseCase(
        repository: AudioFileRepository,
        audioPlayer: AndroidAudioPlayer
    ): PlayAudioFileUseCase {
        return PlayAudioFileUseCase(
            repository,
            audioPlayer)
    }

    @Provides
    @Singleton
    fun provideGetAudioFilesUseCase(repository: AudioFileRepository): GetAudioFilesUseCase {
        return GetAudioFilesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteAllAudioFilesUseCase(
        repository: AudioFileRepository,
        androidAudioRecorder: AndroidAudioRecorder
        ): DeleteAllAudioFilesUseCase {
        return DeleteAllAudioFilesUseCase(repository,androidAudioRecorder)
    }


    @Provides
    @Singleton
    fun provideLastRecordedAudioFileUseCase(
        repository: AudioFileRepository,
        audioPlayer: AndroidAudioPlayer
    ): PlayLastRecordedAudioUseCase {
        return PlayLastRecordedAudioUseCase(repository, audioPlayer)
    }

    @Provides
    @Singleton
    fun provideAndroidAudioPlayer(
        @ApplicationContext context: Context
    ): AndroidAudioPlayer {
        return AndroidAudioPlayer(context)
    }

    @Provides
    @Singleton
    fun provideAndroidAudioRecorder(
        @ApplicationContext context: Context
    ): AndroidAudioRecorder {
        return AndroidAudioRecorder(context)
    }



}