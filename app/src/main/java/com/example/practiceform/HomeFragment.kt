package com.example.practiceform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.practiceform.data.User
import com.example.practiceform.data.source.loacal.AppDatabase


class HomeFragment : Fragment() {


    private var appDB : AppDatabase? = null
    private var userList = listOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appDB= AppDatabase.getInstance(this)

        // 데이터에 읽고 쓸때는 쓰레드 사용
        val r = Runnable {
            userList=appDB?.userDao()?.getAll()!!
        }
        val thread = Thread(r)
        thread.start()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

}// Required empty public constructor