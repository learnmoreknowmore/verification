package cn.zhsa.common.utils

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

object FragmentUtils {
    fun addFragment(activity: AppCompatActivity, fragment: Fragment?, @IdRes containerId: Int) {
        val manager = activity.supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.add(containerId, fragment!!)
        transaction.commit()
    }
}