package com.yigu.shop.commom.result;

import java.util.List;

/**
 * Created by brain on 2017/1/15.
 */
public class MapiOrderItem extends MapiBaseResult {

    private String goods_name;
    private String goods_number;
    private String goods_price;
    private String goods_attr;
    private String goods_img;
    private String formated_goods_price;
    private String goods_id;
    private MapiResourceResult img_info;
    private String formated_shop_price;

    public String getFormated_shop_price() {
        return formated_shop_price;
    }

    public void setFormated_shop_price(String formated_shop_price) {
        this.formated_shop_price = formated_shop_price;
    }

    public MapiResourceResult getImg_info() {
        return img_info;
    }

    public void setImg_info(MapiResourceResult img_info) {
        this.img_info = img_info;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getFormated_goods_price() {
        return formated_goods_price;
    }

    public void setFormated_goods_price(String formated_goods_price) {
        this.formated_goods_price = formated_goods_price;
    }

    public String getGoods_attr() {
        return goods_attr;
    }

    public void setGoods_attr(String goods_attr) {
        this.goods_attr = goods_attr;
    }

    public String getGoods_img() {
        return goods_img;
    }

    public void setGoods_img(String goods_img) {
        this.goods_img = goods_img;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_number() {
        return goods_number;
    }

    public void setGoods_number(String goods_number) {
        this.goods_number = goods_number;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }
}
