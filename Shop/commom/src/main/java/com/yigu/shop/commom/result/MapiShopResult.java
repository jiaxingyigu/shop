package com.yigu.shop.commom.result;

import java.util.List;

/**
 * Created by brain on 2016/8/31.
 */
public class MapiShopResult extends MapiBaseResult{
    private String seller_id;
    private String shop_name;
    private String shop_logo;
    private List<MapiItemResult> goods;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<MapiItemResult> getGoods() {
        return goods;
    }

    public void setGoods(List<MapiItemResult> goods) {
        this.goods = goods;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getShop_logo() {
        return shop_logo;
    }

    public void setShop_logo(String shop_logo) {
        this.shop_logo = shop_logo;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }
}
