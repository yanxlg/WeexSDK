package com.yxlg.weex.bridge.CachePage;

/**
 * Created by Administrator on 2017/7/6 0006.
 */

public enum RenderStateEnum {
    NOTRENDER("NOTRENDER"),
    RENDERING("RENDERING"),
    RENDERSUCCESS("RENDERSUCCESS"),
    RENDERFAILED("RENDERFAILED");
    private String flag;
    RenderStateEnum(String flag) {
        this.flag = flag;
    }
    public String getFlag() {
        return flag;
    }
}