package cn.zhsa.common

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import cn.zhsa.common.utils.DisplayManager
import com.alibaba.android.arouter.launcher.ARouter
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import timber.log.Timber
import timber.log.Timber.DebugTree
import kotlin.properties.Delegates

class BaseApplication : Application() {
    private var refWatcher: RefWatcher? = null

    override fun onCreate() {
        super.onCreate()
        initConfig()
        DisplayManager.init(this)
        registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks)
    }
    private fun setupLeakCanary(): RefWatcher {
        return if (LeakCanary.isInAnalyzerProcess(this)) {
            RefWatcher.DISABLED
        } else LeakCanary.install(this)
    }
    /**
     * 初始化配置
     */
    private fun initConfig() {
        //路由初始化
        if (BuildConfig.DEBUG) {
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this)
        context = applicationContext
        refWatcher = setupLeakCanary()
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else {
            Timber.plant(CrashReportingTree())
        }
    }
    private val mActivityLifecycleCallbacks = object : ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            Timber.d( "onCreated: " + activity.componentName.className)
        }

        override fun onActivityStarted(activity: Activity) {
            Timber.d( "onStart: " + activity.componentName.className)
        }

        override fun onActivityResumed(activity: Activity) {

        }

        override fun onActivityPaused(activity: Activity) {

        }

        override fun onActivityStopped(activity: Activity) {

        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

        }

        override fun onActivityDestroyed(activity: Activity) {
            Timber.d("onDestroy: " + activity.componentName.className)
        }
    }
    companion object {

        private val TAG = this::class.java.canonicalName

        var context: Context by Delegates.notNull()
            private set

        fun getRefWatcher(context: Context): RefWatcher? {
            val myApplication = context.applicationContext as BaseApplication
            return myApplication.refWatcher
        }

    }
}

private class CrashReportingTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        super.log(priority, tag, message, t)
    }
}
