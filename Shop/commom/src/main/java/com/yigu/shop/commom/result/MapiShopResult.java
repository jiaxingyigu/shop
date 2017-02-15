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
    private String goods_count;
    private String new_goods_count;
    private String seller_foller_count;

    private List<MapiItemResult> goods_list;

    public List<MapiItemResult> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<MapiItemResult> goods_list) {
        this.goods_list = goods_list;
    }

    private String logo;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getGoods_count() {
        return goods_count;
    }

    public void setGoods_count(String goods_count) {
        this.goods_count = goods_count;
    }

    public String getNew_goods_count() {
        return new_goods_count;
    }

    public void setNew_goods_count(String new_goods_count) {
        this.new_goods_count = new_goods_count;
    }

    public String getSeller_foller_count() {
        return seller_foller_count;
    }

    public void setSeller_foller_count(String seller_foller_count) {
        this.seller_foller_count = seller_foller_count;
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
