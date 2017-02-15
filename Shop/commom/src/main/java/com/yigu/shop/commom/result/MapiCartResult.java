package com.yigu.shop.commom.result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brain on 2016/9/8.
 */
public class MapiCartResult extends MapiBaseResult{
    private String title;
    private List<MapiItemResult> list= new ArrayList<>();
    private boolean isSel;

    private String seller_id;
    private String shop_name;

    public List<MapiItemResult> getList() {
        return list;
    }

    public void setList(List<MapiItemResult> list) {
        this.list = list;
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
        this.list = items;
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
