package com.yxlg.weex.bridge.CachePage;

import android.content.Context;
import android.view.View;

import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.RenderContainer;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.WXRenderStrategy;
import com.taobao.weex.utils.WXFileUtils;
import com.yxlg.weex.bridge.CWXSDKInstance;
import com.yxlg.weex.bridge.WXAnalyzerDelegate;

import java.util.Map;

/**
 * Created by yanxlg on 2017/7/6 0006.
 */

public class WXPager implements IWXRenderListener{
    public RenderStateEnum stateEnum=RenderStateEnum.NOTRENDER;
    public String jsBundle;
    public CWXSDKInstance mInstance;
    public IRenderCallback renderCallback;
    public WXAnalyzerDelegate mWxAnalyzerDelegate;
    public View weexView;
    public String id;
    private WXPager() {
    }
    /**
     * 根据jsBundle路径确定是否已经创建
     * @param jsBundle
     */
    public WXPager(String jsBundle, String id, Context context, Map<String,Object> options, String jsonString,WXAnalyzerDelegate mWxAnalyzerDelegate) {
        this.id=id;
        this.jsBundle=jsBundle;
        mInstance = new CWXSDKInstance(context,jsBundle,id,options,jsonString,mWxAnalyzerDelegate);//全局Context不会因为Activity收到影响
        this.mWxAnalyzerDelegate=mWxAnalyzerDelegate;
        RenderContainer renderContainer = new RenderContainer(context);
        mInstance.setRenderContainer(renderContainer);
        mInstance.registerRenderListener(this);
        mInstance.setBundleUrl(jsBundle);
        mInstance.setTrackComponent(true);
        stateEnum=RenderStateEnum.RENDERING;
        if(jsBundle.startsWith("http")){
            mInstance.renderByUrl(context.getPackageName(),jsBundle,options,jsonString,WXRenderStrategy.APPEND_ASYNC);
        }else{
            mInstance.render(context.getPackageName(), WXFileUtils.loadAsset(jsBundle,context),options,jsonString,WXRenderStrategy.APPEND_ASYNC);
        }
    }
    public void setRenderListener(IRenderCallback callback){
        this.renderCallback=callback;
    }

    public IRenderCallback getRenderListener(){
        return this.renderCallback;
    }
    @Override
    public void onViewCreated(WXSDKInstance instance, View view) {
        //带有debug模式的view
        View wrappedView = null;
        if(mWxAnalyzerDelegate != null){
            wrappedView = mWxAnalyzerDelegate.onWeexViewCreated(mInstance,view);
        }
        if(wrappedView != null){
            view = wrappedView;
        }
        weexView=view;
    }

    @Override
    public void onRenderSuccess(WXSDKInstance instance, int width, int height) {
        stateEnum=RenderStateEnum.RENDERSUCCESS;
        if(this.renderCallback!=null){
            this.renderCallback.invoke();
        }
        if(null!=this.mWxAnalyzerDelegate){
            this.mWxAnalyzerDelegate.onWeexRenderSuccess(mInstance);
        }
    }

    @Override
    public void onRefreshSuccess(WXSDKInstance instance, int width, int height) {

    }

    @Override
    public void onException(WXSDKInstance instance, String errCode, String msg) {
        stateEnum=RenderStateEnum.RENDERFAILED;
        //初始化失败，view渲染失败
        if(this.renderCallback!=null){
            this.renderCallback.invoke();
        }
        if(null!=this.mWxAnalyzerDelegate){
            this.mWxAnalyzerDelegate.onException(mInstance,errCode,msg);
        }
    }


    public void setWXAnalyzerDelegate(WXAnalyzerDelegate analyzerDelegate){
        this.mWxAnalyzerDelegate=analyzerDelegate;
        mInstance.setWXAnalyzerDelegate(analyzerDelegate);
        //需要进行初始化并且将view传递给新实例

    }
    public void destroy(){
        this.stateEnum=RenderStateEnum.NOTRENDER;
        this.jsBundle=null;
        this.mInstance=null;
        this.renderCallback=null;
        this.weexView=null;
        this.id=null;
    }
}
