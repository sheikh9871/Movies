package com.example.buildProduct.di.module

import com.example.buildProduct.database.Database
import com.example.buildProduct.movies.data.MoviesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DaoModule {

    @Provides
    @Singleton
    fun provideMoviesDao(db: Database): MoviesDao {
        return db.moviesDao()
    }
}