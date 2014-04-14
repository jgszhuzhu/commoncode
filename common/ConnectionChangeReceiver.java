package com.wowotuan.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.util.Log;
import android.widget.Toast;

/**
 * 检测网络状态的连接情况
 * @author  朱继洋
 * 2013-3-21
 * QQ 7617812
 */
/**
 * 
 * 使用方式一： 1. 在Activity的onCreate中: //注册网络监听 IntentFilter filter = new
 * IntentFilter(); filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
 * registerReceiver(mNetworkStateReceiver, filter); 2. 在Activity中的onDestroy中:
 * //取消监听 unregisterReceiver(mNetworkStateReceiver);
 */
public class ConnectionChangeReceiver extends BroadcastReceiver {

	private static final String TAG = ConnectionChangeReceiver.class
			.getSimpleName();

	public void onReceive(Context context, Intent intent) {
		Log.e(TAG, "网络状态改变");
		boolean success = false;
		// 获得网络连接服务
		ConnectivityManager connManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		// State state = connManager.getActiveNetworkInfo().getState();
		// 获取WIFI网络连接状态 判断信号种类太多舍去
		/*
		 * State state =
		 * connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
		 * .getState(); // 判断是否正在使用WIFI网络 if (State.CONNECTED == state) {
		 * success = true; } // 获取GPRS网络连接状态 state =
		 * connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
		 * .getState(); // 判断是否正在使用GPRS网络 if (State.CONNECTED == state) {
		 * success = true; }
		 */
		NetworkInfo net = connManager.getActiveNetworkInfo();
		if (net != null) {
			success = true;
		} else {
			success = false;
		}

		if (!success) {
			Toast.makeText(context, "当前网络不可用,请检查网络", Toast.LENGTH_SHORT).show();
		}

	}
}
