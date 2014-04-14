package com.wowotuan.db;

import android.net.Uri;

public class ConstantInfo {
	public static final int VIEW_HISTORY = 7;
	public static final int VIEW_HOT = 8;
	
	//百度地图
	 public static final String strKey = "Af5LLlqBAziP0Dw0qdNVUPGF";
		public static final String websit = "http://www.wetexchina.com/MapSignServ/mainservice.asmx";
		//public static final String ashxUrl = "/UploadImgHandler.ashx";
		
	public static boolean DEBUG = true;
	public final static int LISTVIEW_ACTION_INIT = 0x01;
	public final static int LISTVIEW_ACTION_REFRESH = 0x02;
	public final static int LISTVIEW_ACTION_SCROLL = 0x03;
	public final static int LISTVIEW_ACTION_CHANGE_CATALOG = 0x04;
	
	public final static int LISTVIEW_DATA_MORE = 0x01;
	public final static int LISTVIEW_DATA_LOADING = 0x02;
	public final static int LISTVIEW_DATA_FULL = 0x03;
	public final static int LISTVIEW_DATA_EMPTY = 0x04;
	public static final int PAGE_SIZE = 10;// 默认分页大小
	//http://www.hxtgo.com/app_2_0_2_0/api.php

	
	public static  final  String parenturl="http://www.hxtgo.com/";
	public static final String userurl = "http://www.hxtgo.com/app_2_0/api.php?s=User/";// 用户相关
	public static final String tuanurl = "http://www.hxtgo.com/app_2_0/api.php?s=Tuan/";// 团模块
	public static  final  String imageurl="http://www.hxtgo.com/static/";//图片地址
	public static  final  String imageurl1="http://www.hxtgo.com";//图片地址1
	public static final String bannerurl="http://www.hxtgo.com/app_2_0/api.php?s=Ad/TopImageAdList";//首页banner
	public static  final String bannertexturl="http://www.hxtgo.com/app_2_0/api.php?s=Ad/LetterAdList";//首页分类下面的文字广告
	public static final  String homeorderurl="http://www.hxtgo.com/app_2_0/api.php?s=Ad/RecommandAdList";//精选榜单
	public static final String adurl="http://www.hxtgo.com/app_2_0/api.php?s=Ad/TopImageAdList";//获取头部轮播广告
	public static final String adtexturl ="http://www.hxtgo.com/app_2_0/api.php?s=Ad/LetterAdList";//文字广告,type: 1代表横向，2代表坚向
	public static final String adlanmuurl ="http://www.hxtgo.com/app_2_0/api.php?s=Ad/RecommandAdList";//栏目分类广告
	public static final String commenturl ="http://www.hxtgo.com/app_2_0/api.php?s=Comment/getTeamComments";//获取商品的评论
	public static final String usercommenturl ="http://www.hxtgo.com/app_2_0/api.php?s=/Comment/getUserComments";//获取用户已评论数据
	public static final String qqloginurl="http://www.hxtgo.com/thirdpart/qq_sns/callback.php";//qq登陆
	public static final String moreurl=	"http://www.hxtgo.com/app_2_0/api.php?s=/More";//更多
	public static final String commentbaseurl ="http://www.hxtgo.com/app_2_0/api.php?s=Comment/";//评论
	public static  final String tuandetailmobile ="http://www.hxtgo.com/app_2_0/api.php?s=Tuan/detail_mobile&id=";
	public static final String thirdlogign ="http://www.hxtgo.com/thirdpart/qq_sns/callback_app.php?";
	public static class Config {
		public static final boolean DEVELOPER_MODE = false;
	}
	
	
	public static final int COUPON_TYPE_ALL = 40;
	public static final int COUPON_TYPE_MISER = 45;
	public static final int AROUND_TYPE_COUPON = 5;
	public static final int AROUND_TYPE_STORE = 25;
	public static final int STORE_ALL = 50;
	public static final int STORE_SIGN = 55;
	public static final int SEARCH_RESULT_ALL = 60;
	public static final int SEARCH_RESULT_COUPON = 65;

	public static final int AROUND_TYPE_GROUP = 15;
	public static final int AROUND_TYPE_BANKCOUPON = 35;

	public static final int AROUND_RESET = -1;
	public static final int COUPON_RESET = -1;
	public static final int STORE_RESET = -1;
	public static final int RESTE = -1;

	public static final String ENTITY_IS_STORE = "isStore";
	public static final String ENTITY_SHOP = "shop_entity";
	public static final int INVALID_VLAUE = -1;

	public static final int LISTVIEW_RESET = -1;
	public static final int LISTVIEW_ADD = 1;

	public static final int VIEW_ALL_STORE = 0;
	public static final int VIEW_SIGN_STORE = 5;
	public static final int VIEW_AROUND_STORE = 1;
	public static final int VIEW_AROUND_COUPON = 2;
	public static final int VIEW_ALL_COUPON = 3;
	public static final int VIEW_MISER_COUPON = 4;
	public static final int VIEW_SEARCH = 6;

	public static final int VIEW_STORE_COLLECT = 9;
	public static final int VIEW_STORE_COMMENT = 10;
	public static final int VIEW_COUPON_COMMENT = 11;
	

	public static final String CONSTANT_DISID = "constant_disid";
	public static final String CONSTANT_SHOPID = "CONSTANT_SHOPID";

	public static final String ADDRESS = "address_name";

	public static final String author = "com.hexingo.com";
	public static String keyword_tablename = "keyword_table";
	public static final String path = "content://" + author + "/"
			+ keyword_tablename;
	public static final int TABLE_TYPE = 1000;
	public static final String INFO_TABLE_TYPE = "vnd.android.cursor.dir/info_table";
	public static final Uri uri = Uri.parse(path);
	
	
	
	public  static  final  String database_path ="/data/data/com.groupbuy.hexingtuan/databases/dldclient";
	
	public  static  final  int DBVERSION=6;
	
	public static final   String  keywordtablename="keyword_table";
	
	public static  final  String storecollecttablename="storecollect";
	
	
	

	
	
	public   static   final   String  path1 ="content://"+author+"/"+storecollecttablename;
	public  static final  int  TABLE_TYPE1 =1001;
	public  static  final  String LOGIN_TABLE_TYPE="vnd.android.cursor.dir/storecollect";
	public  static final  Uri  uri1 = Uri.parse(path1);
	
	public static  final 	String  couponviewpointtablename="couponviewpoint";  
	public   static   final   String  path2 ="content://"+author+"/"+couponviewpointtablename;
	public  static final  int  TABLE_TYPE2 =1002;
	public  static  final  String COUPONVIEWPOINT_TABLE_TYPE="vnd.android.cursor.dir/couponviewpoint";
	public  static final  Uri  uri2 = Uri.parse(path2);
}
