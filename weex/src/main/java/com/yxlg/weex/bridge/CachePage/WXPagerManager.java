package com.yxlg.weex.bridge.CachePage;

import android.app.Activity;
import android.view.ViewGroup;

import com.yxlg.weex.bridge.WXAnalyzerDelegate;
import com.yxlg.weex.bridge.WXSDKManager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by yanxlg on 2017/7/6 0006.
 * 不能通过bundleJs来判断，当出现多例的时候不好处理
 */

public class WXPagerManager {
    public static Map<String, WXPager> wxPagerMap = new HashMap<>();

    public static void addPager(WXPager wxPager) {
        if (!exist(wxPager)) {
            wxPagerMap.put(wxPager.id, wxPager);
        }
    }

    public static boolean exist(String id) {
        return wxPagerMap.containsKey(id);
    }

    public static boolean exist(WXPager wxPager) {
        return wxPagerMap.containsKey(wxPager.id);
    }


    public static void preload(String jsBundle,String id, Map<String, Object> options, String jsonString){
        if(exist(id)){
            return;
        }
        WXAnalyzerDelegate wxAnalyzerDelegate=new WXAnalyzerDelegate(WXSDKManager.topActivity);
        WXPager wxPager = new WXPager(jsBundle, id, WXSDKManager.topActivity.getApplicationContext(), options, jsonString,wxAnalyzerDelegate);
        addPager(wxPager);
    }


    public static WXPager append(final ViewGroup container, String jsBundle, String id, final IAppendCallback appendCallback, Map<String, Object> options, String jsonString) {
        final WXPager wxPager;
        if (exist(id)) {
            System.out.println("exist");
            wxPager = wxPagerMap.get(id);
//            wxPager.setWXAnalyzerDelegate(mWxAnalyzerDelegate);//更新没用啊，更新不能解决问题不会进行初始化了
            switch (wxPager.stateEnum) {
                case RENDERING:
                    IRenderCallback renderCallback = new IRenderCallback() {
                        @Override
                        public void invoke() {
                            appendCallback.invoke(wxPager.stateEnum);
                            if (wxPager.stateEnum == RenderStateEnum.RENDERSUCCESS) {
                                if (null != wxPager.weexView.getParent()) {
                                    ((ViewGroup) wxPager.weexView.getParent()).removeAllViews();
                                }
                                container.addView(wxPager.weexView);
                            }
                        }
                    };
                    wxPager.setRenderListener(renderCallback);
                    break;
                case RENDERFAILED:
                case RENDERSUCCESS:
                    if (wxPager.stateEnum == RenderStateEnum.RENDERSUCCESS) {
                        if (null != wxPager.weexView.getParent()) {
                            ((ViewGroup) wxPager.weexView.getParent()).removeAllViews();
                        }
                        container.addView(wxPager.weexView);
                    }
                    appendCallback.invoke(wxPager.stateEnum);
                    break;
                case NOTRENDER:
                    break;
            }
        } else {
            System.out.println("create");
            WXAnalyzerDelegate wxAnalyzerDelegate=new WXAnalyzerDelegate(container.getContext());
            wxPager = new WXPager(jsBundle, id, container.getContext().getApplicationContext(), options, jsonString,wxAnalyzerDelegate);
            IRenderCallback renderCallback = new IRenderCallback() {
                @Override
                public void invoke() {
                    if (wxPager.stateEnum == RenderStateEnum.RENDERSUCCESS) {
                        if (null != wxPager.weexView.getParent()) {
                            ((ViewGroup) wxPager.weexView.getParent()).removeAllViews();
                        }
                        container.addView(wxPager.weexView);
                    }
                    appendCallback.invoke(wxPager.stateEnum);
                }
            };
            wxPager.setRenderListener(renderCallback);
            addPager(wxPager);
        }
        return wxPager;
    }

    public static void clear() {
        wxPagerMap.clear();
    }
}
