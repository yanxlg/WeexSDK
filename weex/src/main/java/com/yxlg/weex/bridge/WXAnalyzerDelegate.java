/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.yxlg.weex.bridge;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.analyzer.WeexDevOptions;

import java.lang.reflect.Method;


public final class WXAnalyzerDelegate {
    private WeexDevOptions mWXAnalyzer;
    private static boolean ENABLE = false;

    @SuppressWarnings("unchecked")
    public WXAnalyzerDelegate(@Nullable Context context) {
        if(!ENABLE){
            return;
        }
        if(context == null){
            return;
        }
        mWXAnalyzer=new WeexDevOptions(context);
    }

    public void onCreate() {
        if (mWXAnalyzer == null) {
            return;
        }
        mWXAnalyzer.onCreate();
    }


    public void onStart() {
        if (mWXAnalyzer == null) {
            return;
        }
        mWXAnalyzer.onStart();
    }

    public void onResume() {
        if (mWXAnalyzer == null) {
            return;
        }
        mWXAnalyzer.onResume();
    }


    public void onPause() {
        if (mWXAnalyzer == null) {
            return;
        }
        mWXAnalyzer.onPause();
    }

    public void onStop() {
        if (mWXAnalyzer == null) {
            return;
        }
        mWXAnalyzer.onStop();
    }

    public void onDestroy() {
        if (mWXAnalyzer == null) {
            return;
        }
        mWXAnalyzer.onDestroy();
    }

    public void onWeexRenderSuccess(@Nullable WXSDKInstance instance) {
        if (mWXAnalyzer == null || instance == null) {
            return;
        }
        mWXAnalyzer.onWeexRenderSuccess(instance);
    }
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (mWXAnalyzer == null) {
            return false;
        }
        return mWXAnalyzer.onKeyUp(keyCode,event);
    }
    public void onException(WXSDKInstance instance, String errCode, String msg) {
        if (mWXAnalyzer == null) {
            return;
        }
        if (TextUtils.isEmpty(errCode) && TextUtils.isEmpty(msg)) {
            return;
        }
        mWXAnalyzer.onException(instance,errCode,msg);
    }
    public View onWeexViewCreated(WXSDKInstance instance, View view) {
        if (mWXAnalyzer == null || instance == null || view == null) {
            return null;
        }
        return mWXAnalyzer.onWeexViewCreated(instance,view);
    }
    public static void setEnabled(boolean enabled){
        ENABLE=enabled;
    }

    public void setTopicActivity(Activity activity){
        if (mWXAnalyzer == null || activity == null) {
            return;
        }
        mWXAnalyzer.setTopicActivity(activity);
    }
}
