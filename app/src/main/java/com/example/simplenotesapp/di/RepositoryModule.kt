package com.example.simplenotesapp.di

import com.example.simplenotesapp.repository.NoteRepository
import com.example.simplenotesapp.repository.NoteRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindNoteRepository(
        impl: NoteRepositoryImp
    ): NoteRepository
}

