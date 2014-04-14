package com.wowotuan.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONObject;

import com.wowotuan.db.ConstantInfo;
import com.wowotuan.db.SharePersistent;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;

import android.location.Location;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

/**
 * 将地图左边解析为地址
 * 
 * @author dld
 * 
 */
public class MapUtil {
	

	public static void getBaiduAddress(final Context context, final Handler handler,
			final double lat,final  double lon) {

		HandlerThread ht = new HandlerThread("");
		ht.start();
		final Handler handler1 = new Handler(ht.getLooper());
		handler1.post(new Runnable() {

			@Override
			public void run() {
				try {
					URL url = new URL(
							"http://api.map.baidu.com/geocoder?output=json&location="
									+ lat + ","
									+ lon + "&key=" + ConstantInfo.strKey);
					URLConnection httpcnn = url.openConnection();
					httpcnn.setConnectTimeout(1000 * 15);
					InputStream input = url.openStream();
					StringBuilder sb = new StringBuilder();
					char[] buff = new char[1024];
					int len = 0;
					InputStreamReader inreader = new InputStreamReader(input);
					while ((len = inreader.read(buff)) > 0) {
						sb.append(buff, 0, len);
					}
					input.close();
					if (!StringUtils.isEmpty(sb.toString())) {

						JSONObject jobj = null;

						// 判断是不是假网络 或者根本没有网络，连接的户外的网络
						if (sb.toString().trim().length() > 1
								&& !sb.toString().contains("html")) {
							jobj = new JSONObject(sb.toString());
						} else {
							return;
						}

						JSONObject object = jobj.getJSONObject("result");
						JSONObject object2 = object
								.getJSONObject("addressComponent");
						String cityName = object2.getString("city");
						String address = object.getString("formatted_address")
								.substring(
										3,
										object.getString("formatted_address")
												.length()); // 周边显示我的位置

						Log.v("test", cityName + address);
						SharePersistent.getInstance().savePerference(context,
								"cityname", cityName, "address", address);
						// 发送信息给UI页面更新UI
						if (lat > 0
								&& lon> 0
								&& address != null) {
							Message msg = new Message();
							msg.obj = address;
							msg.what = 1;
							handler.sendMessage(msg);
						}
					}
				} catch (Exception localException) {
					handler.sendEmptyMessage(0);
					localException.printStackTrace();
				}
			}
		});

	}
	
	
	

}
