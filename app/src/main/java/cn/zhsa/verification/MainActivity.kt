package cn.zhsa.verification

import androidx.fragment.app.Fragment
import cn.zhsa.common.base.BaseActivity
import cn.zhsa.common.router.RouterMap
import cn.zhsa.common.utils.FragmentUtils
import cn.zhsa.home.HomeFragment
import com.alibaba.android.arouter.facade.annotation.Route

@Route(path = RouterMap.HOME)
class MainActivity : BaseActivity() {
    override fun layoutId(): Int = R.layout.activity_collect

    override fun initData() {
    }

    override fun initView() {
        addFragment()
    }

    override fun start() {
    }

    private fun addFragment() {
        val homeFragment: Fragment = getHomeFragment()
        FragmentUtils.addFragment(this, homeFragment, R.id.container)
    }

    private fun getHomeFragment(): Fragment {
        // 获取Fragment
        return HomeFragment.getInstance()
    }
}