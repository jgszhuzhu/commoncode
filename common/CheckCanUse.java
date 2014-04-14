package com.wowotuan.common;



import com.wowotuan.db.SharePersistent;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class CheckCanUse {
	public static  boolean canUse(Context  context){
		if (SharePersistent.getInstance().getPerference(context, "time")!=null 
				&& SharePersistent.getInstance().getPerference(context, "time").length()>0) {
			String numString  = SharePersistent.getInstance().getPerference(context, "time");
			try {
				int  num=  Integer.parseInt(numString);
				if (num<100) {
					return  true;
				}else {
					return  false;
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return true;
		
	}
	public static void updateTime(Context  context){
		if (SharePersistent.getInstance().getPerference(context, "time")!=null
				&& SharePersistent.getInstance().getPerference(context, "time").length()>0) {
			String numString  = SharePersistent.getInstance().getPerference(context, "time");
			try {
				int  num=  Integer.parseInt(numString);
				if (num<100) {
					num++;
					SharePersistent.getInstance().savePerference(context, "time", String.valueOf(num));
				}else {
					
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}else {
			SharePersistent.getInstance().savePerference(context, "time", "0");
		}
	}
	

}	
