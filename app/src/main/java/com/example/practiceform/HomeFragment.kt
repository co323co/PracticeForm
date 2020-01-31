package com.example.practiceform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.practiceform.model.User
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_user.view.*


class HomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView =  inflater.inflate(R.layout.fragment_home, container, false)
        var rv = rootView.findViewById<RecyclerView>(R.id.main_RecyclerView)

        var userList = ArrayList<User>()
        rv.setHasFixedSize(true)
        var rvAdapter = UserAdapter(userList)
        rv.adapter = rvAdapter
        rv.layoutManager = LinearLayoutManager(this.context) as RecyclerView.LayoutManager

        val appDB=Room.databaseBuilder(this.context!!,AppDatabase::class.java,"app_db").allowMainThreadQueries().build()
        val userDao = appDB.userDao()

        //rvAdapter.addUsers(userDao.getAll())

        var btn =rootView.findViewById<Button>(R.id.btn_main_insert)

        btn.setOnClickListener {
            var FirstName = rootView.findViewById<TextInputEditText>(R.id.et_FirstName).text.toString()
            var LastName = rootView.findViewById<TextInputEditText>(R.id.et_LastName).text.toString()
            val user = User(null, FirstName,LastName)
            rvAdapter.userList.add(user)
            //리사이클러뷰 새로고침을 위해서 뷰 레이아웃을 모두 지워주고 아덥터를 다시 붙인다
            rv.removeAllViewsInLayout()
            rv.adapter = rvAdapter

            //userDao.insertOne(user)
            //val users = userDao.getAll() as ArrayList<User>
            //Toast.makeText(this.context,users.size.toString() ,Toast.LENGTH_LONG).show()
            //rvAdapter.addUsers(userDao.getAll())
        }
        return rootView
    }

    //수정 전
/*
    class UserAdapter(private val users: List<User>) :
        RecyclerView.Adapter<UserAdapter.Holder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
            return Holder(view)
        }

        override fun getItemCount(): Int {
            return users.size
        }

        override fun onBindViewHolder(holder: Holder, position: Int) {
            holder.bind(users[position])
        }

        class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var userID = itemView.tv_item_UserID.text
            var firstName = itemView.tv_item_FirstName.text
            var lastName = itemView.tv_item_LastName.text

            fun bind(user: User) {
                userID = user.uid.toString()
                firstName = user.firstName
                lastName = user.lastName
            }
        }
    }


 */

    class UserAdapter(
        val userList: ArrayList<User>
    ) : RecyclerView.Adapter<UserAdapter.UserVH>() {

        //클래스 선언
        class UserVH(view: View) : RecyclerView.ViewHolder(view) {}

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserVH {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_user, parent, false)

            return UserVH(view)
        }

        override fun onBindViewHolder(holder: UserVH, position: Int) {
            holder.itemView.tv_item_UserID.text = userList[position].uid.toString()
            holder.itemView.tv_item_FirstName.text = userList[position].firstName.toString()
            holder.itemView.tv_item_LastName.text = userList[position].lastName.toString()
        }

        override fun getItemCount(): Int {
            return userList.size
        }

        fun addUsers(users: List<User>) {
            userList.clear()
            userList.addAll(users)
            notifyDataSetChanged()
        }
    }

}// Required empty public constructor


