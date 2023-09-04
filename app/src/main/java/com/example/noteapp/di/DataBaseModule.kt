package com.example.noteapp.di

import android.content.Context
import androidx.room.Room
import com.example.noteapp.data.Constants
import com.example.noteapp.data.db.LocalDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent :: class)
object DataBaseModule {

    @Provides
    @Singleton
    fun providesDataBase(
        @ApplicationContext context : Context
    )
    = Room.databaseBuilder(
        context.applicationContext,
        LocalDataBase::class.java,
        Constants.LOCAL_DB
    ).build()

    @Provides
    @Singleton
    fun provideDao(dataBase: LocalDataBase)  = dataBase.noteDao()

}