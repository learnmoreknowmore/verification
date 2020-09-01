package cn.zhsa.common.utils

import android.content.Context
import cn.zhsa.common.router.RouterMap
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor
import com.alibaba.android.arouter.launcher.ARouter

@Interceptor(priority = 1, name = "重新分组进行拦截")
class BaseInterceptor : IInterceptor {
    override fun process(postcard: Postcard, callback: InterceptorCallback) {
//        if (postcard.extra == ConstantMap.LOGIN_EXTRA) {
//            val isLogin = postcard.extras.getBoolean(ConstantMap.IS_LOGIN)
//            if (!isLogin) {
//                ARouter.getInstance().build(RouterMap.LOGIN).navigation()
//            } else {
//                postcard.withString(ConstantMap.IS_LOGIN_EXTRA, "登录了!")
//                callback.onContinue(postcard)
//            }
//        } else {
            callback.onContinue(postcard)
//        }
    }

    override fun init(context: Context) {}
}