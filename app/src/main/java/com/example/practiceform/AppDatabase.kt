package com.example.practiceform

import android.content.Context
import android.widget.Toast
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.practiceform.dao.UserDao
import com.example.practiceform.model.User

@Database(entities = arrayOf(User::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java, "app.db")
                        .fallbackToDestructiveMigration()
                            //메인쓰레드에서 돌아가게하려면 이거 쓰고 아니면 db접근은 스레드안에 구현(단 메인스레드에서 처리하면 UI반응이 늦어질 수 있음)
                        .allowMainThreadQueries()
                        .build()
                    }
                }
            return INSTANCE
        }
        fun destroyInstance() {
            INSTANCE = null
            if(INSTANCE==null)
            Toast.makeText(App.context(),"DB삭제",Toast.LENGTH_SHORT).show()
        }
    }

}
