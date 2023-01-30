package au.com.safetychampion.data.di

import au.com.safetychampion.data.data.local.RoomDataSource
import au.com.safetychampion.data.data.local.room.AppDatabase
import org.koin.dsl.module

val dbModule = module {
    single { get<AppDatabase>(AppDatabase::class).storableDao() }
    single { get<AppDatabase>(AppDatabase::class).syncableDao() }
    single { RoomDataSource() }
}
