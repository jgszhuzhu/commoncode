package com.wowotuan.db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 
 * 
 * 
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {

	/**
	 * 
	 * @param context
	 *            上下文
	 * @param name
	 *            database的地址
	 * @param factory
	 * @param version
	 */
	private Context context;

	public DatabaseOpenHelper(Context context, String table, String s, int i) {
		super(context, table, null, i);
		this.context = context;
		copydatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
	
	private ExecutorService executorService  = Executors.newFixedThreadPool(5);
  /**
   * 拷贝数据库到目录下
   */
  private void copydatabase() {
    if (SharePersistent.getInstance().getPerference(
        context, "firstboot") == null
        || (!SharePersistent.getInstance()
            .getPerference(context, "firstboot")
            .equals("false"))) {
      executorService.execute(new Runnable() {
        @Override
        public void run() {
          copyDabaseToLocalDir();
          SharePersistent.getInstance().savePerference(
              context, "firstboot", "false");
        }
      });
    }

  }

  /**
   * 拷贝数据库
   */
  public void copyDabaseToLocalDir() {
    try {
      InputStream input = context.getAssets().open(
          "dldclient");

      File file = context
          .getDatabasePath("dldclient").getParentFile();

      if (!file.exists()) {
        file.mkdir();
      }
      FileOutputStream fout = new FileOutputStream(
          context.getDatabasePath("dldclient"));
      byte[] buff = new byte[1024];
      int len = 0;
      while ((len = input.read(buff)) > 0) {
        fout.write(buff, 0, len);
      }

      fout.close();
      input.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
