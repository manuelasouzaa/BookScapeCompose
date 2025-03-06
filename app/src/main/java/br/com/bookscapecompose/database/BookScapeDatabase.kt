package br.com.bookscapecompose.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.bookscapecompose.database.dao.SavedBookDao
import br.com.bookscapecompose.database.dao.UserDao
import br.com.bookscapecompose.model.SavedBook
import br.com.bookscapecompose.model.User

@Database(entities = [(User::class), (SavedBook::class)], version = 1)
abstract class BookScapeDatabase : RoomDatabase() {

    abstract fun UserDao() : UserDao
    abstract fun SavedBookDao() : SavedBookDao

    companion object {
        @Volatile
        private var INSTANCE: BookScapeDatabase? = null

        fun getDatabaseInstance(context: Context): BookScapeDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    BookScapeDatabase::class.java,
                    "BookScapeDatabase"
                ).build().also { INSTANCE = it }
            }
    }
}