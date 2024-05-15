package com.example.buildProduct.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.buildProduct.movies.data.Movie
import com.example.buildProduct.movies.data.MoviesDao

@Database(entities = [Movie::class], version = 1, exportSchema = false, )
@TypeConverters(TypeConverter::class)
abstract class Database: RoomDatabase() {

    abstract fun moviesDao(): MoviesDao

}