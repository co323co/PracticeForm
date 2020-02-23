package com.example.practiceform

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practiceform.model.User
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.item_user.view.*
import kotlinx.android.synthetic.main.update_dialog.view.*


class HomeFragment() : Fragment() {

/*
    lateinit var mContext : Context

    constructor(mcontext: Context) : this()
    {
        this.mContext = mcontext
    }


 */

    lateinit var homeView : View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val rootView =  inflater.inflate(R.layout.fragment_home, container, false)

        homeView = rootView
        return homeView
    }

    //onCreateVIew에서는 getActivity로 불러온 context가 아직 null일 수도 있어!
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val rv = homeView.findViewById<RecyclerView>(R.id.main_RecyclerView)
        val userList = ArrayList<User>()
        rv.setHasFixedSize(true)
        val rvAdapter = UserAdapter(userList, context as Context)
        rv.adapter = rvAdapter
        rv.layoutManager = LinearLayoutManager(App.context()) as RecyclerView.LayoutManager

        var appDB=AppDatabase.getInstance(App.context())
        var userDao = appDB?.userDao()
        rvAdapter.refresh()


        /*
      val handler = Handler()
      val r = Runnable() {
              fun run()
              {

                  rvAdapter.addUsers(userDao!!.getAll())

          }
      }
      handler.post(r);

     */

        /*
        val handler1 = Handler()
        Thread {
            val users = appDB?.userDao()?.getAll() as ArrayList<User>
            handler1.post {
                rvAdapter.addUsers(userDao!!.getAll())
            }
        }.start()
         */



        val btn =homeView.findViewById<Button>(R.id.btn_main_insert)

        btn.setOnClickListener {

            val FirstName = homeView.findViewById<TextInputEditText>(R.id.et_FirstName).text.toString()
            val LastName = homeView.findViewById<TextInputEditText>(R.id.et_LastName).text.toString()
            val user = User(null, FirstName,LastName)

            userDao?.insertOne(user)
            rvAdapter.refresh()

           // Toast.makeText(App.context(),"새로고침 완료",Toast.LENGTH_SHORT).show()



            // 쓰레드 보이지 저 쓰레드 실행 전 처리해야될건 onPreEx 머시기 함수1
            // 쓰레드에 시킬 본 작업은 doInBackground 함수 2 doInBackground는 메인쓰레드에서실행되지않아
            //굳이지 이것도 한번봐바 그리고 과제는 낼 내줄게 껏다키면 된다
            // 쓰레드 작업 끈난 후 처리해야될거 onPost머시기 함수 3
            // ㅇㅋ?

        }

        val allDeleteBtn =homeView.findViewById<Button>(R.id.btn_main_allDelete)
        allDeleteBtn.setOnClickListener {
            userDao?.deleteAll()
            //AppDatabase.destroyInstance()
            //appDB=AppDatabase.getInstance(App.context())
            rvAdapter.refresh()
        }


    }

    class UserAdapter(
        // 얘는 프래그먼트 밑에있긴 하지만 클래스라 함수는 못가져와
        // ㅇㅋ?먼말 위에 프래그먼트는 프래그먼ㅌ르르 상속받고있자너 아 ㅇㅋㅇ
        val userList: ArrayList<User>,
        val context: Context) : RecyclerView.Adapter<UserAdapter.UserVH>() {

        //클래스 선언
        class UserVH(view: View) : RecyclerView.ViewHolder(view) {}

        var appDB=AppDatabase.getInstance(App.context())


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserVH {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_user, parent, false)

            return UserVH(view)
        }

        override fun onBindViewHolder(holder: UserVH, position: Int) {
            holder.itemView.tv_item_UserID.text = userList[position].uid.toString()
            holder.itemView.tv_item_FirstName.text = userList[position].firstName.toString()
            holder.itemView.tv_item_LastName.text = userList[position].lastName.toString()

            holder.itemView.btn_update.setOnClickListener {

                val builder: androidx.appcompat.app.AlertDialog.Builder? = androidx.appcompat.app.AlertDialog.Builder(context)
                var inflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                var view : View = inflater.inflate(R.layout.update_dialog,null)

                builder?.setView(view)
                builder?.setTitle("수정")
                builder?.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id ->

                    var appDB=AppDatabase.getInstance(App.context())
                    var userDao = appDB?.userDao()

                    var FirstName = view.et_update_FirstName.text.toString()
                    var LastName = view.et_update_LastName.text.toString()
                    val uid = userList[position].uid
                    userDao?.update(uid,FirstName,LastName)

                    refresh()


                })
                builder?.setNegativeButton("NO", DialogInterface.OnClickListener { dialog, id ->
                    // User clicked OK button
                })
                val dialog : androidx.appcompat.app.AlertDialog? = builder?.create()
                dialog?.show()

            }
            holder.itemView.btn_delete.setOnClickListener {

                appDB?.userDao()?.delete(userList[position])
                refresh()

            }
        }

        override fun getItemCount(): Int {
            return userList.size
        }

        fun refresh() {
            userList.clear()
            val users = appDB?.userDao()?.getAll() as ArrayList<User>
            userList.addAll(users)
            notifyDataSetChanged()

        }
    }

}// Required empty public constructor


