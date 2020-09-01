package cn.zhsa.module_collect

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import cn.zhsa.common.router.RouterMap
import cn.zhsa.module_collect.adapter.CollectAdapter
import cn.zhsa.module_collect.model.CollectBean
import cn.zhsa.module_collect.ui.RectificationNoticeActivity
import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.android.synthetic.main.activity_collect.*
import kotlin.random.Random

@Route(path = RouterMap.COLLECT)
class CollectActivity : AppCompatActivity(), View.OnClickListener,
    SwipeRefreshLayout.OnRefreshListener {

    private val mAdapter: CollectAdapter by lazy {
        CollectAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collect)
        initView()
    }


    private fun initView() {
        initListener()
        rv_item?.adapter = mAdapter
    }

    private fun initListener() {
        mSwipeRefreshLayout?.setOnRefreshListener(this)
        btn_add?.setOnClickListener(this)
        iv_back?.setOnClickListener(this)
        iv_search?.setOnClickListener(this)
    }


    private fun mockData(): List<CollectBean> {
        var list = mutableListOf<CollectBean>()
        for (i in 1..20) {
            list.add(CollectBean(System.currentTimeMillis(), "test", Random.Default.nextInt(0, 4)))
        }
        return list
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_add -> {
                //新增
            }
            R.id.iv_back -> {
                //返回
                finish()
            }
            R.id.iv_search -> {
                //搜索
                startActivity(Intent(this, RectificationNoticeActivity::class.java))
            }
        }
    }

    override fun onRefresh() {
        //请求数据
        mAdapter.submitList(mockData())
    }
}
