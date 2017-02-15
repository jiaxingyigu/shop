package com.yigu.shop.commom.result;

import java.util.List;

/**
 * Created by brain on 2016/12/8.
 */
public class MapiAttrResult extends MapiBaseResult{

    private String attr_id;
    private String attr_name;
    private List<MapiValueResult> value;

    public String getAttr_id() {
        return attr_id;
    }

    public void setAttr_id(String attr_id) {
        this.attr_id = attr_id;
    }

    public String getAttr_name() {
        return attr_name;
    }

    public void setAttr_name(String attr_name) {
        this.attr_name = attr_name;
    }

    public List<MapiValueResult> getValue() {
        return value;
    }

    public void setValue(List<MapiValueResult> value) {
        this.value = value;
    }
}
