package syq.com.weexsdk;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.yxlg.weex.bridge.CachePage.IAppendCallback;
import com.yxlg.weex.bridge.CachePage.RenderStateEnum;
import com.yxlg.weex.bridge.CachePage.WXPager;
import com.yxlg.weex.bridge.CachePage.WXPagerManager;
import com.yxlg.weex.bridge.WXAnalyzerDelegate;

public class Main2Activity extends Activity {
    private WXPager wxPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        wxPager=WXPagerManager.append((ViewGroup) findViewById(R.id.container), "hello.js","helloPage1", new IAppendCallback() {
            @Override
            public void invoke(RenderStateEnum stateEnum) {
                switch (stateEnum){
                    case RENDERSUCCESS:
                        System.out.println("success");
                        break;
                    case RENDERFAILED:
                        System.out.println("failded");
                        break;
                }
            }
        },null,null);
        wxPager.mWxAnalyzerDelegate.setTopicActivity(this);//设置显示的Activity
        wxPager.mInstance.onActivityCreate();
    }

    @Override
    public void onStart() {
        super.onStart();
        if(wxPager.mInstance!=null){
            wxPager.mInstance.onActivityStart();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(wxPager.mInstance!=null){
            wxPager.mInstance.onActivityResume();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        if(wxPager.mInstance!=null){
            wxPager.mInstance.onActivityPause();
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        if(wxPager.mInstance!=null){
            wxPager.mInstance.onActivityStop();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(wxPager.mInstance!=null){
            wxPager.mInstance.onActivityDestroy();
        }
    }
}
