package com.yxlg.weex.bridge;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.taobao.weex.InitConfig;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.adapter.IWXDebugAdapter;
import com.taobao.weex.adapter.IWXHttpAdapter;
import com.taobao.weex.adapter.IWXImgLoaderAdapter;
import com.taobao.weex.adapter.IWXJSExceptionAdapter;
import com.taobao.weex.appfram.websocket.IWebSocketAdapterFactory;
import com.taobao.weex.common.WXException;
import com.yxlg.weex.bridge.CachePage.WXPagerManager;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Administrator on 2017/7/6 0006.
 */

public class WXSDKManager {
    public static Activity topActivity;
    /**
     * 简单初始化
     * @param application
     */
    public static void instance(Application application){
        WXPagerManager.clear();
        //环境添加属性
        WXSDKEngine.addCustomOptions("appName", "WXBridge");
        WXSDKEngine.addCustomOptions("appGroup", "WXBridge");
        WXSDKEngine.initialize(application,
                new InitConfig.Builder()
                        .build()
        );
        //初始化Fresco缓存
        Fresco.initialize(application);
        //启动调试
        WXAnalyzerDelegate.setEnabled(true);
    }

    /**
     * Adapter 初始化
     * @param application
     * @param imgLoaderAdapter
     * @param debugAdapter
     * @param httpAdapter
     * @param jsExceptionAdapter
     * @param webSocketAdapterFactory
     */
    public static void instance(Application application, IWXImgLoaderAdapter imgLoaderAdapter, IWXDebugAdapter debugAdapter,IWXHttpAdapter httpAdapter,  IWXJSExceptionAdapter jsExceptionAdapter, IWebSocketAdapterFactory webSocketAdapterFactory){
        WXPagerManager.clear();
        //环境添加属性
        WXSDKEngine.addCustomOptions("appName", "WXBridge");
        WXSDKEngine.addCustomOptions("appGroup", "WXBridge");
        InitConfig.Builder config=new InitConfig.Builder();
        if(null!=imgLoaderAdapter){
            config.setImgAdapter(imgLoaderAdapter);
        }
        if(null!=debugAdapter){
            config.setDebugAdapter(debugAdapter);
        }
        if(null!=httpAdapter){
            config.setHttpAdapter(httpAdapter);
        }
        if(null!=jsExceptionAdapter){
            config.setJSExceptionAdapter(jsExceptionAdapter);
        }

        if(null!=webSocketAdapterFactory){
            config.setWebSocketAdapterFactory(webSocketAdapterFactory);
        }
        WXSDKEngine.initialize(application,config.build());
        //初始化Fresco缓存
        Fresco.initialize(application);
        //启动调试
        WXAnalyzerDelegate.setEnabled(true);
    }

    public static void registerCompontent(ArrayList<WXComponentAdapter> componentList){
        Iterator<WXComponentAdapter> iterator=componentList.iterator();
        try{
            while (iterator.hasNext()){
                WXComponentAdapter componentAdapter=iterator.next();
                WXSDKEngine.registerComponent(componentAdapter.COMPONENT_NAME,componentAdapter.getClass());
            }
        } catch (WXException e) {
            e.printStackTrace();
        }
    }

    public static void registerModule(ArrayList<WXModuleAdapter> moduleList){
        Iterator<WXModuleAdapter> iterator=moduleList.iterator();
        try{
            while (iterator.hasNext()){
                WXModuleAdapter moduleAdapter=iterator.next();
                WXSDKEngine.registerModule(moduleAdapter.MODULE_NAME,moduleAdapter.getClass());
            }
        } catch (WXException e) {
            e.printStackTrace();
        }
    }

    //debug环境
    public void initDebugEnvironment(boolean connectable, boolean debuggable, String host) {
        if (!"DEBUG_SERVER_HOST".equals(host)) {
            WXEnvironment.sDebugServerConnectable = connectable;
            WXEnvironment.sRemoteDebugMode = debuggable;
            WXEnvironment.sRemoteDebugProxyUrl = "ws://" + host + ":8088/debugProxy/native";
        }
    }
    public static void registerActivityLifecycleCallbacks(Application application, final IActivityLifecycle activityLifecycle){
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks(){
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
                activityLifecycle.onActivityCreated(activity,bundle);
            }

            @Override
            public void onActivityStarted(Activity activity) {
                activityLifecycle.onActivityStarted(activity);
            }

            @Override
            public void onActivityResumed(Activity activity) {
                activityLifecycle.onActivityResumed(activity);
                //记录当前栈顶Activity
                topActivity=activity;
            }

            @Override
            public void onActivityPaused(Activity activity) {
                activityLifecycle.onActivityPaused(activity);
            }

            @Override
            public void onActivityStopped(Activity activity) {
                activityLifecycle.onActivityStopped(activity);
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
                activityLifecycle.onActivitySaveInstanceState(activity,bundle);
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                activityLifecycle.onActivityDestroyed(activity);
                com.taobao.weex.WXSDKManager.getInstance().notifyTrimMemory();
                com.taobao.weex.WXSDKManager.getInstance().notifyTrimMemory();
            }
        });
    }
}
