package syq.com.weexsdk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import com.yxlg.weex.bridge.CachePage.IAppendCallback;
import com.yxlg.weex.bridge.CachePage.RenderStateEnum;
import com.yxlg.weex.bridge.CachePage.WXPager;
import com.yxlg.weex.bridge.CachePage.WXPagerManager;

public class MainActivity extends Activity{

    private WXPager wxPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wxPager=WXPagerManager.append((ViewGroup) findViewById(R.id.container), "hello.js","helloPage", new IAppendCallback() {
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
        wxPager.mInstance.onActivityCreate();
        wxPager.mWxAnalyzerDelegate.setTopicActivity(this);
        findViewById(R.id.testbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Main2Activity.class));
            }
        });
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        WXPagerManager.clear();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        super.onBackPressed();
        if(wxPager.mInstance!=null){
            return wxPager.mInstance.onKeyUp(keyCode,event);
        }else{
            return false;
        }
    }
}
