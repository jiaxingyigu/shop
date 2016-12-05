package com.yigu.shop.commom.result;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 市
 * @author brain
 *
 */
public class CityModel implements Serializable {
	private String region_id;
	private String region_name;
	private List<DistrictModel> _child = new ArrayList<DistrictModel>();
	
	public CityModel() {
		super();
	}

	public CityModel(String region_name, List<DistrictModel> _child) {
		super();
		this.region_name = region_name;
		this._child = _child;
	}

	public String getRegion_name() {
		return region_name;
	}

	public void setRegion_name(String region_name) {
		this.region_name = region_name;
	}

	public String getRegion_id() {
		return region_id;
	}

	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}

	public List<DistrictModel> get_child() {
		return _child;
	}

	public void set_child(List<DistrictModel> _child) {
		this._child = _child;
	}

	@Override
	public String toString() {
		return "CityModel [region_name=" + region_name + ", districtList=" + _child
				+ "]";
	}
	
}
