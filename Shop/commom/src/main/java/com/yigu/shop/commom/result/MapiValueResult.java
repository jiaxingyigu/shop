package com.yigu.shop.commom.result;

import java.io.Serializable;

/**
 * Created by brain on 2017/1/9.
 */
public class MapiValueResult implements Serializable {

    private String label;
    private String price;
    private String format_price;
    private String id;
    private boolean isSel;
    private String value;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isSel() {
        return isSel;
    }

    public void setSel(boolean sel) {
        isSel = sel;
    }

    public String getFormat_price() {
        return format_price;
    }

    public void setFormat_price(String format_price) {
        this.format_price = format_price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
