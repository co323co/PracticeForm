package com.example.practiceform.data.source.loacal

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.practiceform.HomeFragment
import com.example.practiceform.data.User
import android.icu.lang.UCharacter.GraphemeClusterBreak.V



@Database(entities = arrayOf(User::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    //데이터베이스와 연결되는 DAO
    //DAO는 abstract로 "getter"을 제공
    abstract fun userDao(): UserDao

    //singleton pattern, room database는 한개만 존재
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase? {
        if (INSTANCE == null) {
            //동시에 2개의 INSTANCE가 생성되는 것을 막기위한 synchronized
            synchronized(AppDatabase::class.java) {
                if (INSTANCE == null) {
                    //데이터 베이스 생성부분
                    INSTANCE = Room.databaseBuilder(
                        context.getApplicationContext(),
                        AppDatabase::class.java!!, "app_db"
                    )
                        .build()
                }
            }
        }
        return INSTANCE
    }
}
