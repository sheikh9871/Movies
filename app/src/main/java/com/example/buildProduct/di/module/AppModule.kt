package com.example.buildProduct.di.module

import android.content.Context
import androidx.paging.PagedList
import androidx.paging.PagingConfig
import androidx.room.Room
import com.example.buildProduct.database.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): Database {
        return Room.databaseBuilder(context, Database::class.java, "DB").build()
    }

    @Provides
    @Singleton
    fun providePagingConfig(): PagedList.Config {
        return PagedList.Config.Builder()
            .setPageSize(10)
            .setInitialLoadSizeHint(30)
            .setPrefetchDistance(10)
            .setEnablePlaceholders(true)
            .build()
    }

    @Provides
    @Singleton
    fun provideNewPagingConfig(): PagingConfig {
        return PagingConfig(
            pageSize = 20,
            maxSize = 100,
            prefetchDistance = 5,
            enablePlaceholders = true
        )
    }
}