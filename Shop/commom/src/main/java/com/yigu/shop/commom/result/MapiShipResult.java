package com.yigu.shop.commom.result;

/**
 * Created by brain on 2017/1/15.
 */
public class MapiShipResult extends MapiBaseResult{

    private String shipping_id;
    private String shipping_name;
    private String shipping_fee;
    private String format_shipping_fee;
    private boolean isSel;
    private String free_money_val;

    public String getFree_money_val() {
        return free_money_val;
    }

    public void setFree_money_val(String free_money_val) {
        this.free_money_val = free_money_val;
    }

    public boolean isSel() {
        return isSel;
    }

    public void setSel(boolean sel) {
        isSel = sel;
    }

    public String getFormat_shipping_fee() {
        return format_shipping_fee;
    }

    public void setFormat_shipping_fee(String format_shipping_fee) {
        this.format_shipping_fee = format_shipping_fee;
    }

    public String getShipping_fee() {
        return shipping_fee;
    }

    public void setShipping_fee(String shipping_fee) {
        this.shipping_fee = shipping_fee;
    }

    public String getShipping_id() {
        return shipping_id;
    }

    public void setShipping_id(String shipping_id) {
        this.shipping_id = shipping_id;
    }

    public String getShipping_name() {
        return shipping_name;
    }

    public void setShipping_name(String shipping_name) {
        this.shipping_name = shipping_name;
    }
}
