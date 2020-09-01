package cn.zhsa.module_collect.ui

import android.view.View
import cn.zhsa.common.base.BaseActivity
import cn.zhsa.common.router.RouterMap
import cn.zhsa.module_collect.R
import com.alibaba.android.arouter.facade.annotation.Route

@Route(path = RouterMap.RECTIFICATION)
class RectificationNoticeActivity: BaseActivity(),View.OnClickListener {
    override fun layoutId(): Int = R.layout.activity_rectification_notice_search

    override fun initData() {

    }

    override fun initView() {

    }

    override fun start() {

    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.iv_delete->{
                //删除
            }
            R.id.iv_back->{
                //返回
                finish()
            }
        }
    }
}