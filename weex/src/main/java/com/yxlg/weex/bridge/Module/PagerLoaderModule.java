package com.yxlg.weex.bridge.Module;

import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.WXModule;
import com.yxlg.weex.bridge.CachePage.WXPagerManager;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by yanxlg on 2017/7/7 0007.
 * weex加载Module
 */

public class PagerLoaderModule extends WXModule {

    @JSMethod(uiThread = false)
    public void preload(String bundleJs,String id, Map<String,Object> options){
        JSONObject jsonInitData=new JSONObject();
        Iterator<Map.Entry<String,Object>> it=options.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String,Object> item=it.next();
            jsonInitData.put(item.getKey(),item.getValue());
        }
        WXPagerManager.preload(bundleJs,id,options,jsonInitData.toJSONString());
    }
}
