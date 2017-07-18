package com.yigu.shop.commom.result;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.io.Serializable;
import java.util.List;

/**
 * Created by brain on 2017/3/28.
 */
public class MapiJobInfoResult implements IPickerViewData {

    private String id;
    private String name;

    private List<MapiJobInfoResult> list;

    public List<MapiJobInfoResult> getList() {
        return list;
    }

    public void setList(List<MapiJobInfoResult> list) {
        this.list = list;
    }

    //这个用来显示在PickerView上面的字符串,PickerView会通过IPickerViewData获取getPickerViewText方法显示出来。
    @Override
    public String getPickerViewText() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
