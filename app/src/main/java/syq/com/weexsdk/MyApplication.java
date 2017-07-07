package syq.com.weexsdk;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.taobao.weex.InitConfig;
import com.taobao.weex.WXSDKEngine;
import com.yxlg.weex.bridge.CachePage.WXPagerManager;
import com.yxlg.weex.bridge.IActivityLifecycle;
import com.yxlg.weex.bridge.WXAnalyzerDelegate;
import com.yxlg.weex.bridge.WXSDKManager;

/**
 * Created by Administrator on 2017/7/6 0006.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        WXSDKManager.instance(this);
        WXSDKManager.registerActivityLifecycleCallbacks(this,new IActivityLifecycle(){
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
            }
            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    @Override
    public void onTerminate() {
        // 程序终止的时候执行
        System.out.println("程序终止");
        super.onTerminate();
        WXPagerManager.clear();
    }
}
