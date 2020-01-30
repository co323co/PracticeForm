package com.example.practiceform

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_toolbar.*
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.fragment.app.Fragment


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

        var navigationView : NavigationView = findViewById(R.id.main_navigation);
        navigationView.setNavigationItemSelectedListener(this)

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment,HomeFragment())
            .commit()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{ // 메뉴 버튼
                drawer_layout.openDrawer(GravityCompat.START)    // 네비게이션 드로어 열기
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.item1-> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment,OneFragment())
                    .commit()
            }
            R.id.item2-> Toast.makeText(this,"item2 clicked",Toast.LENGTH_SHORT).show()
            R.id.item3-> Toast.makeText(this,"item3 clicked",Toast.LENGTH_SHORT).show()
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
}
