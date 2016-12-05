package com.yigu.shop.commom.result;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 省
 * @author brain
 *
 */
public class ProvinceModel implements Serializable{
	private String region_id;
	private String region_name;
	private List<CityModel> _child = new ArrayList<CityModel>();
	
	public ProvinceModel() {
		super();
	}

	public ProvinceModel(String region_name, List<CityModel> _child) {
		super();
		this.region_name = region_name;
		this._child = _child;
	}

	public List<CityModel> get_child() {
		return _child;
	}

	public void set_child(List<CityModel> _child) {
		this._child = _child;
	}

	public String getRegion_id() {
		return region_id;
	}

	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}

	public String getRegion_name() {
		return region_name;
	}

	public void setRegion_name(String region_name) {
		this.region_name = region_name;
	}

	@Override
	public String toString() {
		return "ProvinceModel [region_name=" + region_name + ", cityList=" + _child + "]";
	}
	
}
