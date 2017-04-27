# WeexSDK
[![](https://jitpack.io/v/yanxlg/WeexSDK.svg)](https://jitpack.io/#yanxlg/WeexSDK)


## changeList
* com.taobao.weex.ui.view.WXScrollView
    ```java
      private void init() {
        setWillNotDraw(false);
        startScrollerTask();
      //显示ScrollView下拉阴影
  //      setOverScrollMode(View.OVER_SCROLL_NEVER);
        childHelper = new NestedScrollingChildHelper(this);
        childHelper.setNestedScrollingEnabled(true);
      }
    ```
* com.taobao.weex.appfram.navigator.WXNavigatorModule
    ```java
       //修改注册Activity的category
       private final static String WEEX = "com.syq.android.intent.category.WEEX";
    ```
