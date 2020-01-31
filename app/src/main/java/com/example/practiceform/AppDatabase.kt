package com.example.practiceform

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.practiceform.dao.UserDao
import com.example.practiceform.model.User

@Database(entities = arrayOf(User::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
/*
    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: HomeFragment): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = context.activity?.applicationContext?.let {
                        Room.databaseBuilder(it, AppDatabase::class.java, "app_db")
                            .build()
                    }
                }
            }
            return INSTANCE
        }
    }

 */
}
