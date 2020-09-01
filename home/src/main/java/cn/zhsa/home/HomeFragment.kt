package cn.zhsa.home

import android.view.View
import cn.zhsa.common.base.BaseFragment
import cn.zhsa.common.router.RouterMap
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment: BaseFragment(), View.OnClickListener {
    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun initView() {
        initListener()
    }
    private fun initListener(){
        iv_offline_collect.setOnClickListener(this)
        iv_rectify_notice.setOnClickListener(this)
    }
    override fun lazyLoad() {
    }
    companion object{
        @Volatile
        private var instance:HomeFragment? = null
        fun getInstance():HomeFragment{
            return instance?: synchronized(this){
                instance?: HomeFragment().also { instance = it }
            }
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.iv_offline_collect->{
                //线下采集
                ARouter.getInstance().build(RouterMap.COLLECT).navigation()
            }
            R.id.iv_rectify_notice->{
                //整改通知书
                ARouter.getInstance().build(RouterMap.RECTIFICATION).navigation()
            }
        }
    }
}