package com.yigu.shop.commom.result;

/**
 * Created by brain on 2016/9/1.
 */
public class MapiItemResult extends MapiBaseResult{
    private boolean isLast = false;
    private boolean isSel;
    private String goods_id;
    private String cat_id;
    private String goods_sn;
    private String market_price;
    private String shop_price;
    private String goods_thumb;
    private String goods_name;
    private String goods_desc;
    private String goods_img;
    private String goods_brief;
    private String seller_id;
    private String is_real;
    private String extension_code;

    public String getExtension_code() {
        return extension_code;
    }

    public void setExtension_code(String extension_code) {
        this.extension_code = extension_code;
    }

    public String getIs_real() {
        return is_real;
    }

    public void setIs_real(String is_real) {
        this.is_real = is_real;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getGoods_brief() {
        return goods_brief;
    }

    public void setGoods_brief(String goods_brief) {
        this.goods_brief = goods_brief;
    }

    public String getGoods_img() {
        return goods_img;
    }

    public void setGoods_img(String goods_img) {
        this.goods_img = goods_img;
    }

    public String getGoods_desc() {
        return goods_desc;
    }

    public void setGoods_desc(String goods_desc) {
        this.goods_desc = goods_desc;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

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

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_sn() {
        return goods_sn;
    }

    public void setGoods_sn(String goods_sn) {
        this.goods_sn = goods_sn;
    }

    public String getGoods_thumb() {
        return goods_thumb;
    }

    public void setGoods_thumb(String goods_thumb) {
        this.goods_thumb = goods_thumb;
    }

    public String getMarket_price() {
        return market_price;
    }

    public void setMarket_price(String market_price) {
        this.market_price = market_price;
    }

    public String getShop_price() {
        return shop_price;
    }

    public void setShop_price(String shop_price) {
        this.shop_price = shop_price;
    }
}
