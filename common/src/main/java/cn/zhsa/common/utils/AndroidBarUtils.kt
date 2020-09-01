package cn.zhsa.common.utils

import android.R
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import androidx.drawerlayout.widget.DrawerLayout

/**
 * @description StatusBar 和 NavigationBar 的工具类
 */
object AndroidBarUtils {
    private const val STATUS_BAR_HEIGHT_RES_NAME = "status_bar_height"
    private const val NAV_BAR_HEIGHT_RES_NAME = "navigation_bar_height"
    private const val NAV_BAR_WIDTH_RES_NAME = "navigation_bar_width"

    /**
     * 设置透明StatusBar,默认文字为白色
     *
     * @param activity Activity
     */
    fun setTranslucent(activity: Activity) {
        setTranslucentStatusBar(activity)
    }

    /**
     * 设置 DrawerLayout 在4.4版本下透明，不然会出现白边
     *
     * @param drawerLayout DrawerLayout
     */
    fun setTranslucentDrawerLayout(drawerLayout: DrawerLayout?) {
        if (drawerLayout != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawerLayout.fitsSystemWindows = true
            drawerLayout.clipToPadding = false
        }
    }

    /**
     * @param activity
     */
    fun hideBottomUIMenu(activity: Activity) {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT < 19) { // lower api
            val v = activity.window.decorView
            v.systemUiVisibility = View.GONE
        } else if (Build.VERSION.SDK_INT >= 19) {
            val decorView = activity.window.decorView
            val uiOptions =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_FULLSCREEN
            decorView.systemUiVisibility = uiOptions
        }
    }

    /**
     * 设置透明StatusBar
     *
     * @param activity Activity
     */
    fun setTranslucentStatusBar(activity: Activity) {
        val window = activity.window ?: return
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { //设置statusBar和navigationBar为黑色
                //View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR |
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else { //设置statusBar为黑色
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                        or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
            )
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            )
        }
    }

    /**
     * 修改状态栏为全透明
     *
     * @param activity
     */
    @TargetApi(19)
    fun transparencyBar(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = activity.window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val window = activity.window
            window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            )
        }
    }

    /**
     * 修改状态栏颜色，支持4.4以上版本
     *
     * @param activity
     * @param colorId
     */
    fun setStatusBarColor(activity: Activity, @ColorRes colorId: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = activity.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = activity.resources.getColor(colorId)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //使用SystemBarTint库使4.4版本状态栏变色，需要先将状态栏设置为透明
            transparencyBar(activity)
        }
        // 着色状态栏 需要为系统状态栏预留控件 防止页面上顶
        val rootView = activity.window.decorView.findViewById<ViewGroup>(R.id.content)
        val view = rootView.getChildAt(0)
        view.fitsSystemWindows = true
    }

    fun setStatusBarBackground(activity: Activity, drawableId: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = activity.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            //window.setStatusBarColor(activity.getResources().getColor(colorId));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //使用SystemBarTint库使4.4版本状态栏变色，需要先将状态栏设置为透明
            transparencyBar(activity)
        }
        //着色状态栏 需要为系统状态栏预留控件 防止页面上顶
        val rootView = activity.window.decorView.findViewById<ViewGroup>(R.id.content)
        val view = rootView.getChildAt(0)
        //view.setBackgroundResource(drawableId);
        view.fitsSystemWindows = true
    }

    /**
     * Android 6.0使用原始的主题适配
     *
     * @param activity Activity
     * @param darkMode 是否是黑色模式
     */
    fun setBarDarkMode(activity: Activity, darkMode: Boolean) {
        val window = activity.window ?: return
        //设置StatusBar模式
        if (darkMode) { // 黑色模式
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // 设置statusBar和navigationBar为黑色
                    window.decorView.systemUiVisibility =
                        View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                } else { // 设置statusBar为黑色
                    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }
            }
        } else { // 白色模式
            var statusBarFlag = View.SYSTEM_UI_FLAG_VISIBLE
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                statusBarFlag = (window.decorView.systemUiVisibility
                        and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv())
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // 设置statusBar为白色，navigationBar为灰色
//                int navBarFlag = window.getDecorView().getSystemUiVisibility()
//                        & ~View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;//如果想让navigationBar为白色，那么就使用这个代码。
                val navBarFlag = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                window.decorView.systemUiVisibility = navBarFlag or statusBarFlag
            } else {
                window.decorView.systemUiVisibility = statusBarFlag
            }
        }
        setHuaWeiStatusBar(darkMode, window)
    }

    /**
     * 设置华为手机 StatusBar
     *
     * @param darkMode 是否是黑色模式
     * @param window   window
     */
    fun setHuaWeiStatusBar(darkMode: Boolean, window: Window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                val decorViewClazz = Class.forName("com.android.internal.policy.DecorView")
                val field = decorViewClazz.getDeclaredField("mSemiTransparentStatusBarColor")
                field.isAccessible = darkMode
                field.setInt(window.decorView, Color.TRANSPARENT) //改为透明
            } catch (e: ClassNotFoundException) {
                Log.e("setHuaWeiStatusBar", "HuaWei status bar 模式设置失败")
            } catch (e: IllegalAccessException) {
                Log.e("setHuaWeiStatusBar", "HuaWei status bar 模式设置失败")
            } catch (e: NoSuchFieldException) {
                Log.e("setHuaWeiStatusBar", "HuaWei status bar 模式设置失败")
            }
        }
    }

    /**
     * 获取状态栏高度
     *
     * @param context context
     * @return 状态栏高度
     */
    fun getStatusBarHeight(context: Context): Int {
        // 获得状态栏高度
        return getBarHeight(context, STATUS_BAR_HEIGHT_RES_NAME)
    }

    /**
     * 获取导航栏高度
     *
     * @param activity activity
     * @return 导航栏高度
     */
    fun getNavigationBarHeight(activity: Activity): Int {
        return if (hasNavBar(activity)) {
            // 获得导航栏高度
            getBarHeight(activity, NAV_BAR_HEIGHT_RES_NAME)
        } else {
            0
        }
    }

    /**
     * 获取横屏状态下导航栏的宽度
     *
     * @param activity activity
     * @return 导航栏的宽度
     */
    fun getNavigationBarWidth(activity: Activity): Int {
        return if (hasNavBar(activity)) {
            // 获得导航栏高度
            getBarHeight(activity, NAV_BAR_WIDTH_RES_NAME)
        } else {
            0
        }
    }

    /**
     * 获取Bar高度
     *
     * @param context context
     * @param barName 名称
     * @return Bar高度
     */
    fun getBarHeight(context: Context, barName: String?): Int {
        // 获得状态栏高度
        val resourceId = context.resources.getIdentifier(barName, "dimen", "android")
        return context.resources.getDimensionPixelSize(resourceId)
    }

    /**
     * 是否有NavigationBar
     *
     * @param activity 上下文
     * @return 是否有NavigationBar
     */
    fun hasNavBar(activity: Activity): Boolean {
        val windowManager = activity.windowManager
        val d = windowManager.defaultDisplay
        val realDisplayMetrics = DisplayMetrics()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            d.getRealMetrics(realDisplayMetrics)
        }
        val realHeight = realDisplayMetrics.heightPixels
        val realWidth = realDisplayMetrics.widthPixels
        val displayMetrics = DisplayMetrics()
        d.getMetrics(displayMetrics)
        val displayHeight = displayMetrics.heightPixels
        val displayWidth = displayMetrics.widthPixels
        return realWidth - displayWidth > 0 || realHeight - displayHeight > 0
    }

    /**
     * 设置BarPaddingTop
     *
     * @param context Activity
     * @param view    View[ToolBar、TitleBar、navigationView.getHeaderView(0)]
     */
    fun setBarPaddingTop(context: Activity, view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val paddingStart = view.paddingStart
            val paddingEnd = view.paddingEnd
            val paddingBottom = view.paddingBottom
            val statusBarHeight = getStatusBarHeight(context)
            //改变titleBar的高度
            val lp = view.layoutParams
            lp.height += statusBarHeight
            view.layoutParams = lp
            //设置paddingTop
            view.setPaddingRelative(paddingStart, statusBarHeight, paddingEnd, paddingBottom)
        }
    }

    /**
     * 创建Navigation Bar
     *
     * @param activity 上下文
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun createNavBar(activity: Activity) {
        val navBarHeight = getNavigationBarHeight(activity)
        val navBarWidth = getNavigationBarWidth(activity)
        if (navBarHeight > 0 && navBarWidth > 0) {
            //创建NavigationBar
            val navBar = View(activity)
            val pl: FrameLayout.LayoutParams
            if (activity.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                pl = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, navBarHeight)
                pl.gravity = Gravity.BOTTOM
            } else {
                pl = FrameLayout.LayoutParams(navBarWidth, ViewGroup.LayoutParams.MATCH_PARENT)
                pl.gravity = Gravity.END
            }
            navBar.layoutParams = pl
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                navBar.setBackgroundColor(Color.parseColor("#fffafafa"))
            } else {
                navBar.setBackgroundColor(Color.parseColor("#40000000"))
            }
            //添加到布局当中
            val decorView = activity.window.decorView as ViewGroup
            decorView.addView(navBar)
            //设置主布局PaddingBottom
            val contentView = decorView.findViewById<ViewGroup>(R.id.content)
            if (activity.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                contentView.setPaddingRelative(0, 0, 0, navBarHeight)
            } else {
                contentView.setPaddingRelative(0, 0, navBarWidth, 0)
            }
        }
    }

    /**
     * 状态栏亮色模式，设置状态栏黑色文字、图标，
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
     *
     * @param activity
     * @return 1:MIUUI 2:Flyme 3:android6.0
     */
    fun StatusBarLightMode(activity: Activity): Int {
        var result = 0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (MIUISetStatusBarLightMode(activity, true)) {
                result = 1
            } else if (FlymeSetStatusBarLightMode(activity.window, true)) {
                result = 2
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                result = 3
            }
        }
        return result
    }

    /**
     * 已知系统类型时，设置状态栏黑色文字、图标。
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
     *
     * @param activity
     * @param type     1:MIUUI 2:Flyme 3:android6.0
     */
    fun StatusBarLightMode(activity: Activity, type: Int) {
        if (type == 1) {
            MIUISetStatusBarLightMode(activity, true)
        } else if (type == 2) {
            FlymeSetStatusBarLightMode(activity.window, true)
        } else if (type == 3) {
            activity.window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        }
    }

    /**
     * 设置状态栏白色字体图标，
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
     *
     * @param activity
     * @return 1:MIUUI 2:Flyme 3:android6.0
     */
    fun statusBarDarkMode(activity: Activity): Int {
        var result = 0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (MIUISetStatusBarLightMode(activity, false)) {
                result = 1
            } else if (FlymeSetStatusBarLightMode(activity.window, false)) {
                result = 2
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                result = 3
            }
        }
        return result
    }

    /**
     * 状态栏暗色模式，清除MIUI、flyme或6.0以上版本状态栏黑色文字、图标
     */
    fun StatusBarDarkMode(activity: Activity, type: Int) {
        if (type == 1) {
            MIUISetStatusBarLightMode(activity, false)
        } else if (type == 2) {
            FlymeSetStatusBarLightMode(activity.window, false)
        } else if (type == 3) {
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        }
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏文字及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    fun FlymeSetStatusBarLightMode(window: Window?, dark: Boolean): Boolean {
        var result = false
        if (window != null) {
            try {
                val lp = window.attributes
                val darkFlag = WindowManager.LayoutParams::class.java
                    .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
                val meizuFlags = WindowManager.LayoutParams::class.java
                    .getDeclaredField("meizuFlags")
                darkFlag.isAccessible = true
                meizuFlags.isAccessible = true
                val bit = darkFlag.getInt(null)
                var value = meizuFlags.getInt(lp)
                value = if (dark) {
                    value or bit
                } else {
                    value and bit.inv()
                }
                meizuFlags.setInt(lp, value)
                window.attributes = lp
                result = true
            } catch (e: Exception) {
            }
        }
        return result
    }

    /**
     * 需要MIUIV6以上
     *
     * @param activity
     * @param dark     是否把状态栏文字及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    fun MIUISetStatusBarLightMode(activity: Activity, dark: Boolean): Boolean {
        var result = false
        val window = activity.window
        if (window != null) {
            val clazz: Class<*> = window.javaClass
            try {
                var darkModeFlag = 0
                val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
                val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
                darkModeFlag = field.getInt(layoutParams)
                val extraFlagField = clazz.getMethod(
                    "setExtraFlags",
                    Int::class.javaPrimitiveType,
                    Int::class.javaPrimitiveType
                )
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag) //状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag) //清除黑色字体
                }
                result = true
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //开发版 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错，所以两个方式都要加上
                    if (dark) {
                        activity.window.decorView.systemUiVisibility =
                            (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                    or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
                    } else {
                        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
                    }
                }
            } catch (e: Exception) {
            }
        }
        return result
    }
}