package com.yigu.shop.commom.result;

/**
 * Created by brain on 2016/9/1.
 */
public class MapiItemResult extends MapiBaseResult{
    private boolean isLast = false;
    private boolean isSel;

    public boolean isSel() {
        return isSel;
    }

    public void setSel(boolean sel) {
        isSel = sel;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }
}
