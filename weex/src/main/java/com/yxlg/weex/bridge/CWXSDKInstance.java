package com.yxlg.weex.bridge;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.CallSuper;
import android.view.KeyEvent;

import com.taobao.weex.WXSDKInstance;
import com.yxlg.weex.bridge.CachePage.WXPager;
import com.yxlg.weex.bridge.CachePage.WXPagerManager;

import java.util.Map;

/**
 * Created by yanxlg on 2017/7/7 0007.
 * 基于阿里WXSDKInstance 封装
 */

public class CWXSDKInstance extends WXSDKInstance {
    public String bundleUrl;
    public String id;
    public Context mContext;
    public Map<String,Object> options;
    public String jsonString;
    protected WXAnalyzerDelegate mWxAnalyzerDelegate;
    public CWXSDKInstance(Context context) {
        super(context);
    }
    public CWXSDKInstance(Context context,String bundleUrl,String id,Map<String,Object> options,String jsonString,WXAnalyzerDelegate mWxAnalyzerDelegate) {
        super(context);
        this.mWxAnalyzerDelegate = mWxAnalyzerDelegate;
        this.mContext=context;
        this.bundleUrl=bundleUrl;
        this.id=id;
        this.options=options;
        this.jsonString=jsonString;
    }
    public void setWXAnalyzerDelegate(WXAnalyzerDelegate analyzerDelegate){
        this.mWxAnalyzerDelegate=analyzerDelegate;
    }
    @Override
    public void onActivityCreate() {
        super.onActivityCreate();
        if(mWxAnalyzerDelegate != null){
            mWxAnalyzerDelegate.onCreate();
        }
    }

    @Override
    public void onActivityDestroy() {
        super.onActivityDestroy();
        //关闭这个就将销毁当前实例并且重新创建新的实例
        //重新创建新的实例
        //获取RenderListener
        WXPager cachePager=new WXPager(bundleUrl, id, mContext, options, jsonString,mWxAnalyzerDelegate);
        WXPagerManager.wxPagerMap.remove(id);
        WXPagerManager.wxPagerMap.put(id,cachePager);
    }

    @Override
    public void onActivityPause() {
        super.onActivityPause();
        if(mWxAnalyzerDelegate != null){
            mWxAnalyzerDelegate.onPause();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onActivityResume() {
        super.onActivityResume();
        if(mWxAnalyzerDelegate != null){
            mWxAnalyzerDelegate.onResume();
        }
    }

    @Override
    public void onActivityStart() {
        super.onActivityStart();
        if(mWxAnalyzerDelegate != null){
            mWxAnalyzerDelegate.onStart();
        }
    }

    @Override
    public void onActivityStop() {
        super.onActivityStop();
        if(mWxAnalyzerDelegate != null){
            mWxAnalyzerDelegate.onStop();
        }
    }
    @Override
    public boolean onActivityBack() {
        return super.onActivityBack();
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return (mWxAnalyzerDelegate != null && mWxAnalyzerDelegate.onKeyUp(keyCode,event));
    }
}
