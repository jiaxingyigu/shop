package com.yigu.shop.commom.result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brain on 2016/9/8.
 */
public class MapiCartResult extends MapiBaseResult{
    private String title;
    private List<MapiItemResult> cart_goods= new ArrayList<>();
    private boolean isSel;

    private String seller_id;
    private String shop_name;;

    public List<MapiItemResult> getCart_goods() {
        return cart_goods;
    }

    public void setCart_goods(List<MapiItemResult> cart_goods) {
        this.cart_goods = cart_goods;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public MapiCartResult(){

    }

    public MapiCartResult(List<MapiItemResult> items) {
        super();
        this.cart_goods = items;
    }

    public boolean isSel() {
        return isSel;
    }

    public void setSel(boolean sel) {
        isSel = sel;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
