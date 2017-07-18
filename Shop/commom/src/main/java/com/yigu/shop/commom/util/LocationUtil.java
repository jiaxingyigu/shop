package com.yigu.shop.commom.util;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

import android.app.Service;
import android.content.Context;
import android.os.Vibrator;

public class LocationUtil {

	private LocationClient mLocationClient;
	private MyLocationListener mMyLocationListener;
	private Vibrator mVibrator;
	private LocationMode tempMode = LocationMode.Hight_Accuracy;//LocationMode.Battery_Saving
	private String tempcoor="gcj02";//"bd09ll":百度经纬度标准


	public LocationUtil(Context context) {
		super();
		mLocationClient = new LocationClient(context);
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		mVibrator =(Vibrator)context.getSystemService(Service.VIBRATOR_SERVICE);
		initLocation();
	}

	/**
	 * 开始定位
	 */
	public void startLoc(){
		mLocationClient.start();//定位SDK start之后会默认发起一次定位请求，开发者无须判断isstart并主动调用request
        mLocationClient.requestLocation();
	}
	
	public void stopLoc(){
		mLocationClient.stop();
	}

	private void initLocation(){
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(tempMode);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		option.setCoorType(tempcoor);//可选，默认gcj02，设置返回的定位结果坐标系，
		option.setScanSpan(0);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
		option.setOpenGps(true);//可选，默认false,设置是否使用gps
		option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
		option.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
		mLocationClient.setLocOption(option);
	}

	/**
	 * 实现实时位置回调监听
	 */
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			if(null!=locationListener)
				locationListener.localInfo(location);
		}
	}

	private OnLocationListener locationListener;

	public interface OnLocationListener{
		public void localInfo(BDLocation location);
	}

	public void setOnReceivedMessageListener(OnLocationListener locationListener){
		this.locationListener = locationListener;
	}

}
