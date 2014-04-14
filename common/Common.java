package com.wowotuan.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


public class Common {


	public static void attachNavigate(int paramInt, Class<? extends Activity> paramClass) {
		setOnclickListener(ActivityManager.getCurrent().findViewById(paramInt), paramClass);
	}



	/**
	 * 跳转到call页面
	 * 
	 * @param paramString
	 */
	public static void call(final String paramString) {
		int length = 0;
		if (!StringUtils.isEmpty(paramString)) {
			Object localObject2 = Pattern.compile("[0-9\\-]+").matcher(paramString);
			Object localObject1 = new ArrayList();
			while (((Matcher) localObject2).find())
				((List) localObject1).add(((Matcher) localObject2).group());
			localObject2 = new String[((List) localObject1).size()];
			// ((List)localObject1).toArray(localObject2);
			length = ((String) localObject2).getBytes().length;
			if (length != 0) {
				if (length <= 1) {
					localObject1 = new Intent("android.intent.action.DIAL");
					((Intent) localObject1).setData(Uri.parse("tel:" + paramString));
					ActivityManager.getCurrent().startActivity((Intent) localObject1);
				} else {
					new AlertDialog.Builder(ActivityManager.getCurrent())
							.setTitle("请选择要拨打的电话")
							.setSingleChoiceItems((Integer) localObject2, 0,
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface paramDialogInterface, int paramInt) {
											Intent localIntent = new Intent("android.intent.action.DIAL");
											localIntent.setData(Uri.parse("tel:" + paramString));
											ActivityManager.getCurrent().startActivity(localIntent);
											paramDialogInterface.dismiss();
										}
									}).create().show();
				}
			} 
			
		} else {
	
		}
	}

	

	public static void goSmsActivity(String paramString, Activity paramActivity) {
		Intent localIntent = new Intent("android.intent.action.VIEW");
		localIntent.putExtra("sms_body", paramString);
		localIntent.setType("vnd.android-dir/mms-sms");
		paramActivity.startActivity(localIntent);
	}

	public static void hideKeyboard(View paramView) {
		//((InputMethodManager) ActivityManager.getCurrent().getSystemService("input_method")).hideSoftInputFromWindow(paramView.getWindowToken(), 0);
	}

	

	private static void setOnclickListener(View paramView, final Class<? extends Activity> paramClass) {
		paramView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View paramView) {
				Common.startActivity(paramClass);
			}
		});
	}

	public static void share() {
		String[] arrayOfString = new String[3];
		arrayOfString[0] = "分享到新浪微博";
		arrayOfString[1] = "分享到腾讯微博";
		arrayOfString[2] = "短信分享";
		final Activity localActivity = ActivityManager.getCurrent();
		new AlertDialog.Builder(localActivity).setTitle("分享")
				.setItems(arrayOfString, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface paramDialogInterface, int paramInt) {
						switch (paramInt) {
						/*case 0:
							ShareActivity.doLogin();
							break;
						case 1:
							TencentWeiboActivity.initLogin(false);
							break;*/
						case 2:
							Common.goSmsActivity((String) Cache.remove("weibo_content"), localActivity);
						}
					}
				}).show();
	}



	public static void startActivity(Class<? extends Activity> paramClass) {
		Activity localActivity = ActivityManager.getCurrent();
		Intent localIntent = new Intent();
		localIntent.setClass(localActivity, paramClass);
		localActivity.startActivity(localIntent);
	}
}
