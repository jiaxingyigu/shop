package com.yigu.shop.commom.result;

import java.util.List;

/**
 * Created by brain on 2016/9/26.
 */
public class MapiSortResult extends MapiBaseResult{
    private String brand_id;
    private String brand_name;
    private String brand_logo;
    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    private List<MapiSortChildResult> children;

    public List<MapiSortChildResult> getChildren() {
        return children;
    }

    public void setChildren(List<MapiSortChildResult> children) {
        this.children = children;
    }

    public String getBrand_logo() {
        return brand_logo;
    }

    public void setBrand_logo(String brand_logo) {
        this.brand_logo = brand_logo;
    }

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
