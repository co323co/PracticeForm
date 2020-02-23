package com.example.practiceform.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.practiceform.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): User

    @Insert
    fun insertOne(user: User)

    @Insert
    fun insertAll(vararg users: User)

    @Delete
    fun delete(user: User)

    @Query("DELETE FROM user")
    fun deleteAll()

    @Query("UPDATE user SET first_name=:first, last_name=:last WHERE uid LIKE :uid")
    fun update(uid:Int?, first:String, last:String)
    //@Query("Set = '1000'")
    //fun setIdZero()

    /*
    fun test() {
        Toast.makeText(App.context(),"userDao 메소드 호출 성공", Toast.LENGTH_SHORT).show()
    }
    */
}