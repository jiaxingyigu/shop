package com.yigu.shop.commom.result;

import java.util.List;

/**
 * Created by brain on 2016/9/10.
 */
public class MapiOrderResult extends MapiBaseResult{

    private String author;
    private String content;
    private String create;
    private String re_content;

    private String order_id;
    private String order_sn;
    private String total_fee;
    private String formated_shipping_fee;

    private String type;

    private MapiAddrResult address;

    private String shipping_time;
    private String pay_time;

    private String await_pay;
    private String await_ship;
    private String shipped;
    private String finished;

    public String getAwait_ship() {
        return await_ship;
    }

    public void setAwait_ship(String await_ship) {
        this.await_ship = await_ship;
    }

    public String getFinished() {
        return finished;
    }

    public void setFinished(String finished) {
        this.finished = finished;
    }

    public String getShipped() {
        return shipped;
    }

    public void setShipped(String shipped) {
        this.shipped = shipped;
    }

    public String getAwait_pay() {
        return await_pay;
    }

    public void setAwait_pay(String await_pay) {
        this.await_pay = await_pay;
    }

    public String getShipping_time() {
        return shipping_time;
    }

    public void setShipping_time(String shipping_time) {
        this.shipping_time = shipping_time;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public MapiAddrResult getAddress() {
        return address;
    }

    public void setAddress(MapiAddrResult address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFormated_shipping_fee() {
        return formated_shipping_fee;
    }

    public void setFormated_shipping_fee(String formated_shipping_fee) {
        this.formated_shipping_fee = formated_shipping_fee;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    private List<MapiOrderItem> goods_list;

    public List<MapiOrderItem> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<MapiOrderItem> goods_list) {
        this.goods_list = goods_list;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreate() {
        return create;
    }

    public void setCreate(String create) {
        this.create = create;
    }

    public String getRe_content() {
        return re_content;
    }

    public void setRe_content(String re_content) {
        this.re_content = re_content;
    }
}
