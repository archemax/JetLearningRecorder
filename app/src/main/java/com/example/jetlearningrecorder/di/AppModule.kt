package com.example.jetlearningrecorder.di

import android.content.Context
import androidx.room.Room
import com.example.jetlearningrecorder.data.audioPlayer.AndroidAudioPlayer
import com.example.jetlearningrecorder.data.data_source.AudioFileDatabase
import com.example.jetlearningrecorder.data.repository.AudioFileRepositoryImpl
import com.example.jetlearningrecorder.domain.repository.AudioFileRepository
import com.example.jetlearningrecorder.domain.useCase.DeleteAllAudioFilesUseCase
import com.example.jetlearningrecorder.domain.useCase.GetAudioFilesUseCase
import com.example.jetlearningrecorder.domain.useCase.InsertAudioFileUseCase
import com.example.jetlearningrecorder.domain.useCase.PlayAudioFileUseCase
import com.example.jetlearningrecorder.domain.useCase.PlayLastRecordedAudioUseCase
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
    fun provideDeleteAllAudioFilesUseCase(repository: AudioFileRepository): DeleteAllAudioFilesUseCase {
        return DeleteAllAudioFilesUseCase(repository)
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



}