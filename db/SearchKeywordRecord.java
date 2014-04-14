package com.wowotuan.db;

import java.util.Calendar;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;

public class SearchKeywordRecord {
	private String author = "com.hexingo.com";
	private Uri uri;
	private ContentResolver resolver;
	private ContentValues values;
	private Context context;
	private String paramString;
	private Handler handler;

	public SearchKeywordRecord(Context context, String paramString,
			Handler handler) {
		this.context = context;
		this.paramString = paramString;
		this.handler = handler;
		getData();
	}

	public SearchKeywordRecord(Context context, String paramString) {
		this.context = context;
		this.paramString = paramString;

		getData();
	}

	public void getData() {
		uri = Uri.parse("content://" + author + "/keyword_table");

		resolver = context.getContentResolver();
		values = new ContentValues();
		Cursor cursor = resolver.query(uri, new String[] { "keyword" }, "keyword=" + "'" + paramString + "'", null, null);
		// select * from keyword_table where keyword='折扣';
		Cursor cursor2 = resolver.query(uri, new String[] { "keyword", "times" }, "keyword=" + "'" + paramString + "'", null, null);

		if (cursor.getCount() == 0) {
			if (cursor2.getCount() >= 20) {
				resolver.delete(uri, "id > 19", null);
			}

			values.put("keyword", paramString);
			Calendar calendar = Calendar.getInstance();
			values.put("times", 1);
			values.put("time", calendar.getTimeInMillis());
			resolver.insert(uri, values);
			// 修改完成的同时发送消息给UI线程，更新数据
			if (handler != null) {
				Message m = Message.obtain();
				m.what = ConstantInfo.VIEW_HISTORY;
				m.arg1 = 1;
				m.arg2 = 1;
				handler.sendMessage(m);
			}

		}
		// 已经存在的就添加记录次数
		if (cursor2 != null && cursor2.getCount() > 0) {
			cursor2.moveToFirst();
			do {
				// System.out.println(cursor2.getString(1)+cursor2.getShort(0));
				int times = Integer.parseInt(cursor2.getString(1));
				values.put("times", ++times);
				resolver.update(uri, values, "keyword=" + "'" + paramString + "'", null);
			} while (cursor2.moveToNext());

		}
		cursor.close();
		cursor2.close();
	}

}
