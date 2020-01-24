package ru.farkhodkhaknazarov.elegion.data.room.utils

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.farkhodkhaknazarov.gitviewer.activities.LoginActivity
import ru.farkhodkhaknazarov.gitviewer.data.api.github.entities.Repo
import ru.farkhodkhaknazarov.gitviewer.data.api.github.entities.UserToken
import ru.farkhodkhaknazarov.gitviewer.data.room.dao.RepoDao
import ru.farkhodkhaknazarov.gitviewer.data.room.dao.UserTokenDao

@Database(entities = arrayOf(UserToken::class, Repo::class), version = 1, exportSchema = false)
@TypeConverters(TypeConverter::class)
abstract class RoomSingleton : RoomDatabase() {
    abstract fun tokensDao(): UserTokenDao
    abstract fun reposDao(): RepoDao

    companion object {
        private var INSTANCE: RoomSingleton? = null
        fun getInstance(context: Context): RoomSingleton {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    RoomSingleton::class.java,
                    "roomdb"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as RoomSingleton
        }
    }
}