package cn.zhsa.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.zhsa.common.utils.FragmentUtils

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        addFragment()
    }

    private fun addFragment() {
        FragmentUtils.addFragment(this,HomeFragment.getInstance(),R.id.container)
    }
}