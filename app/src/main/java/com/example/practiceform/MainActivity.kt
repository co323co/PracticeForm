package com.example.practiceform

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_toolbar.*
import kotlinx.android.synthetic.main.search_dialog.view.*


//참고사이트
//https://bongcando.tistory.com/entry/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-DrawerLayout%EC%9D%84-%EC%82%AC%EC%9A%A9%ED%95%B4%EC%84%9C-%EB%A9%94%EB%89%B4%EB%A5%BC-%EB%A7%8C%EB%93%A4%EC%96%B4%EB%B3%B4%EC%9E%90
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 툴바를 액티비티의 앱바로 지정(툴바 ID)
        setSupportActionBar(main_layout_toolbar)
        // 드로어를 꺼낼 홈 버튼 활성화
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // 홈버튼 이미지 변경, indicator = 지표, 방향 표시등
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
        // 툴바에 타이틀 안보이게
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val navigationView : NavigationView = findViewById(R.id.main_navigation);
        navigationView.setNavigationItemSelectedListener(this)

        //초기 진입시 HomeFragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment,HomeFragment())
            .commit()

    }

    //툴바의 메뉴(-아이콘) 버튼이 클릭 됐을 때 콜백
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{ // 메뉴 버튼
                drawer_layout.openDrawer(GravityCompat.START)    // 네비게이션 드로어 열기//
            }
            R.id.menu_search->{ // 검색 버튼
                //Toast.makeText(applicationContext,"서치 클릭",Toast.LENGTH_LONG).show()

                //액티비티에서는 바로 getSystemService, 프래그먼트에서는 activitiy.getSystemService, 다른 곳에서는 컨텍스트.getSystemService
                val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val view = inflater.inflate(R.layout.search_dialog, null)
                val alertDialog = AlertDialog.Builder(this)
                    .setTitle("검색")
                    .setPositiveButton("확인") { dialog, which ->
                        var search : String = view.et_search.text.toString()

                        val fragment: Fragment = HomeFragment() // Fragment 생성
                        val bundle = Bundle(1) // 파라미터는 전달할 데이터 개수
                        bundle.putString("search", search) // key , value
                        fragment.setArguments(bundle)
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_fragment,fragment)
                            .commit()
                    }
                    .setNeutralButton("취소", null)
                    .create()
                alertDialog.setView(view)
                alertDialog.show()

                /*
                val builder: androidx.appcompat.app.AlertDialog.Builder? = androidx.appcompat.app.AlertDialog.Builder(applicationContext)
                var inflater : LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                var view : View = inflater.inflate(R.layout.search_dialog,null)

                builder?.setView(view)
                Toast.makeText(applicationContext,"셋 뷰",Toast.LENGTH_LONG).show()
                builder?.setTitle("검색")
                builder?.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id ->

                })
                builder?.setNegativeButton("NO", DialogInterface.OnClickListener { dialog, id ->
                    // User clicked OK button
                })
                val dialog = builder?.create()
                dialog?.show()
                 */
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        //TODO 그 뒤로가면 다른 화면 뜨게 해주는 것 구현(그림 연결되어있던거)
        when(item.itemId){

            R.id.home-> {
                supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment,HomeFragment())
                .commit()
            }
            R.id.item1-> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment,OneFragment())
                    .commit()
            }
            R.id.item2-> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment,TwoFragment())
                    .commit()
            }
            R.id.item3-> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment,ThreeFragment())
                    .commit()
            }
        }
        return false
    }

    //지금까지 구현된 상태로는 드로어가 나와있어도 뒤로가기 버튼을 누를시 앱이 종료되어 버린다.
    //드로어가 나와있을때 뒤로가기 버튼에 대한 이벤트를 처리해준다.
    override fun onBackPressed() { //뒤로가기 처리
        if(drawer_layout.isDrawerOpen(GravityCompat.START)){
            drawer_layout.closeDrawers()
            // 테스트를 위해 뒤로가기 버튼시 Toast 메시지
            Toast.makeText(this,"back btn clicked",Toast.LENGTH_SHORT).show()
        } else{
            super.onBackPressed()
        }
    }

    //툴바 메뉴 버튼을 설정
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

}
