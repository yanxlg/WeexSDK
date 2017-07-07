package com.yxlg.weex.bridge;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Administrator on 2017/7/6 0006.
 */

public interface IActivityLifecycle {
    void onActivityCreated(Activity activity, Bundle bundle);
    void onActivityStarted(Activity activity);
    void onActivityResumed(Activity activity);
    void onActivityPaused(Activity activity);
    void onActivityStopped(Activity activity);
    void onActivitySaveInstanceState(Activity activity, Bundle bundle);
    void onActivityDestroyed(Activity activity);
}
