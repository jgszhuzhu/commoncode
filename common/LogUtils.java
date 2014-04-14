package com.wowotuan.common;

import android.util.Log;

public class LogUtils {
	public static final boolean LOGVV = true;

	public static void e(String paramString, Exception paramException) {
		Log.e(paramString, "", paramException);
	}

	public static void e(String paramString1, String paramString2,
			Throwable paramThrowable) {
		Log.e(paramString1, paramString2, paramThrowable);
	}

	public static void log(Object paramObject) {
		Log.v("test", paramObject.toString());
		//Log.v("test", paramObject.toString());
		//Log.v("test1", paramObject.toString());
	}
	public static void log1(Object paramObject) {
		Log.v("test1", paramObject.toString());
	}


	public static void v(String paramString, Object paramObject) {
		Log.v(paramString, paramObject.toString());
	}
}
