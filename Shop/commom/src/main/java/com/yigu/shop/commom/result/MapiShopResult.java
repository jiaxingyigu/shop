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
    private boolean isSel = false;
    private String shop_goods_total;
    private String shop_goods_new;
    private String concer_num;

    private String logo;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getConcer_num() {
        return concer_num;
    }

    public void setConcer_num(String concer_num) {
        this.concer_num = concer_num;
    }

    public String getShop_goods_new() {
        return shop_goods_new;
    }

    public void setShop_goods_new(String shop_goods_new) {
        this.shop_goods_new = shop_goods_new;
    }

    public String getShop_goods_total() {
        return shop_goods_total;
    }

    public void setShop_goods_total(String shop_goods_total) {
        this.shop_goods_total = shop_goods_total;
    }

    public boolean isSel() {
        return isSel;
    }

    public void setSel(boolean sel) {
        isSel = sel;
    }

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
