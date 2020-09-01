package cn.zhsa.common.service;


import com.alibaba.android.arouter.launcher.ARouter;

import cn.zhsa.common.router.RouterMap;

public class StoreModuleRouterService {

    public static boolean isLogin() {
        IStoreModuleService chatModuleService = (IStoreModuleService) ARouter.getInstance().build(RouterMap.STORE_MODULE_SERVICE).navigation();
        return chatModuleService != null && chatModuleService.isLogin();
    }

    public static void setLogin(boolean login) {
        IStoreModuleService chatModuleService = (IStoreModuleService) ARouter.getInstance().build(RouterMap.STORE_MODULE_SERVICE).navigation();
        if (chatModuleService != null) {
            chatModuleService.setLogin(login);
        }
    }
}
