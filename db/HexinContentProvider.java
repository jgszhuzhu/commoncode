package com.wowotuan.db;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class HexinContentProvider extends ContentProvider {
	private DatabaseOpenHelper openHelper;
	
	private Cursor cursor;
	private SQLiteDatabase database;

	// 添加urimatcher用于判断是从哪个表选择
	static UriMatcher uriMatcher;
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(ConstantInfo.author, ConstantInfo.keywordtablename,
				ConstantInfo.TABLE_TYPE);
		uriMatcher.addURI(ConstantInfo.author, ConstantInfo.storecollecttablename,
				ConstantInfo.TABLE_TYPE1);
		uriMatcher.addURI(ConstantInfo.author, ConstantInfo.couponviewpointtablename, ConstantInfo.TABLE_TYPE2);

		
	}
	
	
	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		openHelper = new DatabaseOpenHelper(getContext(), ConstantInfo.database_path, null, 6);
		//判断数据库中是否有表，如果没有就添加
		/*if (!tabIsExist("couponviewpoint")) {
			
			String viewpointcreate =
	"CREATE TABLE 'couponviewpoint' ('time'  TEXT NOT NULL,'hate'  TEXT,'love'  TEXT,'disid'  TEXT NOT NULL,'id'  INTEGER NOT NULL,PRIMARY KEY ('id'));";
			openHelper.getWritableDatabase().execSQL(viewpointcreate);
		}
		if (!tabIsExist("storecollect")) {
			String storecollectcreate =
					"CREATE TABLE 'storecollect' ('title'  TEXT,'address'  TEXT,'imageadd'  TEXT,'tel'  TEXT,'storeid'  TEXT NOT NULL,'id'  INTEGER NOT NULL,PRIMARY KEY ('id'));";
			openHelper.getWritableDatabase().execSQL(storecollectcreate);
		}
		//然后更新数据库版本
		int  version  =  openHelper.getWritableDatabase().getVersion();
		openHelper.onUpgrade(openHelper.getWritableDatabase(), version, version+1);*/
		return true;
	}
	
	
	
	/**
     * 判断某张表是否存在
     * @param tabName 表名
     * @return
     */
    public boolean tabIsExist(String tabName){
            boolean result = false;
            if(tabName == null){
                    return false;
            }
            SQLiteDatabase db = null;
            Cursor cursor = null;
            try {
                    db = openHelper.getReadableDatabase();
                    String sql = "select count(*) as c from sqlite_master where type ='table' and name ='"+tabName.trim()+"' ";
                    cursor = db.rawQuery(sql, null);
                    if(cursor.moveToNext()){
                            int count = cursor.getInt(0);
                            if(count>0){
                                    result = true;
                            }
                    }
                    
                    
                    cursor.close();
                    
            } catch (Exception e) {
                    // TODO: handle exception
            }                
            return result;
    }

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		String tablename = null;
		database  =  openHelper.getWritableDatabase();
		switch (uriMatcher.match(uri)) {
		case ConstantInfo.TABLE_TYPE:
			tablename  =  ConstantInfo.keywordtablename;
			break;
		case ConstantInfo.TABLE_TYPE1:
			tablename  =  ConstantInfo.storecollecttablename;
			break;
		case ConstantInfo.TABLE_TYPE2:
			tablename  =  ConstantInfo.couponviewpointtablename;
			break;
		
		default:
			break;
		}
		
		database.insert(tablename, null, values);
		database.close();
		return null;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		String tablename = null;
		database  =  openHelper.getWritableDatabase();
		switch (uriMatcher.match(uri)) {
		case ConstantInfo.TABLE_TYPE:
			tablename  =  ConstantInfo.keywordtablename;
			break;
		case ConstantInfo.TABLE_TYPE1:
			tablename  =  ConstantInfo.storecollecttablename;
			break;
		case ConstantInfo.TABLE_TYPE2:
			tablename  =  ConstantInfo.couponviewpointtablename;
			break;
		default:
			break;
		}
		
	
		cursor = database.query(tablename, projection, selection,
				selectionArgs, null, null, sortOrder);
		
		return cursor;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		String tablename = null;
		database  =  openHelper.getWritableDatabase();
		switch (uriMatcher.match(uri)) {
		case ConstantInfo.TABLE_TYPE:
			tablename  =  ConstantInfo.keywordtablename;
			break;
		case ConstantInfo.TABLE_TYPE1:
			tablename  =  ConstantInfo.storecollecttablename;
			break;
		case ConstantInfo.TABLE_TYPE2:
			tablename  =  ConstantInfo.couponviewpointtablename;
			break;
		default:
			break;
		}
		int  isdelete =database.delete(tablename, selection, selectionArgs);
		database.close();
		return  isdelete;
	}

	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)) {
		case ConstantInfo.TABLE_TYPE:
			return ConstantInfo.INFO_TABLE_TYPE;
		case ConstantInfo.TABLE_TYPE1:
			return ConstantInfo.LOGIN_TABLE_TYPE;
		case ConstantInfo.TABLE_TYPE2:
			return ConstantInfo.COUPONVIEWPOINT_TABLE_TYPE;

		default:
			break;
		}
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		String tablename = null;
		database  =  openHelper.getWritableDatabase();
		switch (uriMatcher.match(uri)) {
		case ConstantInfo.TABLE_TYPE:
			tablename  =  ConstantInfo.keywordtablename;
			break;
		case ConstantInfo.TABLE_TYPE1:
			tablename  =  ConstantInfo.storecollecttablename;
			break;
		case ConstantInfo.TABLE_TYPE2:
			tablename  =  ConstantInfo.couponviewpointtablename;
			break;
		default:
			break;
		}
		int  isupdate =database.update(tablename, values, selection,
				selectionArgs);
		database.close();
		return isupdate;
	}

}
