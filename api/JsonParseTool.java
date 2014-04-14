package com.wowotuan.api;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import com.wowotuan.common.LogUtils;
import com.wowotuan.db.SharePersistent;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

/**
 * 数据获取类
 * 
 * @author 朱继洋 QQ7617812 2013-5-20
 */
public class JsonParseTool {
	private static final int TIMEOUT = 10000;// 10秒

	/**
	 * 传送文本,例如Json,xml等
	 * 
	 * @param urlPath
	 * @param txt
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public static String sendText(String urlPath, String txt, String encoding)
			throws Exception {
		byte[] sendData = txt.getBytes();
		URL url = new URL(urlPath);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setConnectTimeout(TIMEOUT);
		// 如果通过post提交数据，必须设置允许对外输出数据
		conn.setDoOutput(true);
		conn.setRequestProperty("Content-Type", "text/xml");
		conn.setRequestProperty("Charset", encoding);
		conn.setRequestProperty("Content-Length",
				String.valueOf(sendData.length));
		OutputStream outStream = conn.getOutputStream();
		outStream.write(sendData);
		outStream.flush();
		outStream.close();
		if (conn.getResponseCode() == 200) {
			// 获得服务器响应的数据
			BufferedReader in = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), encoding));
			// 数据
			String retData = null;
			String responseData = "";
			while ((retData = in.readLine()) != null) {
				responseData += retData;
			}
			in.close();
			return responseData;
		}
		return "sendText error!";
	}

	/**
	 * 上传文件
	 * 
	 * @param urlPath
	 * @param filePath
	 * @param newName
	 * @return
	 * @throws Exception
	 */
	public static String upSendFile(String urlPath, String filePath,
			String newName) throws Exception {
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		URL url = new URL(urlPath);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		/* 允许Input、Output，不使用Cache */
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false);
		/* 设置传送的method=POST */
		con.setRequestMethod("POST");
		/* setRequestProperty */
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");
		con.setRequestProperty("Content-Type", "multipart/form-data;boundary="
				+ boundary);
		/* 设置DataOutputStream */
		DataOutputStream ds = new DataOutputStream(con.getOutputStream());
		ds.writeBytes(twoHyphens + boundary + end);
		ds.writeBytes("Content-Disposition: form-data; "
				+ "name=\"file1\";filename=\"" + newName + "\"" + end);
		ds.writeBytes(end);
		/* 取得文件的FileInputStream */
		FileInputStream fStream = new FileInputStream(filePath);
		/* 设置每次写入1024bytes */
		int bufferSize = 1024;
		byte[] buffer = new byte[bufferSize];
		int length = -1;
		/* 从文件读取数据至缓冲区 */
		while ((length = fStream.read(buffer)) != -1) {
			/* 将资料写入DataOutputStream中 */
			ds.write(buffer, 0, length);
		}
		ds.writeBytes(end);
		ds.writeBytes(twoHyphens + boundary + twoHyphens + end);

		/* close streams */
		fStream.close();
		ds.flush();

		/* 取得Response内容 */
		InputStream is = con.getInputStream();
		int ch;
		StringBuffer b = new StringBuffer();
		while ((ch = is.read()) != -1) {
			b.append((char) ch);
		}
		/* 关闭DataOutputStream */
		ds.close();
		return b.toString();
	}


	
	/**
	 * 通过get方式提交参数给服务器
	 * 
	 * @param urlPath
	 * @param params
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public static String sendGetRequest(Context context, String urlPath,
			Map<String, String> params) throws Exception {
		// 使用StringBuilder对象
		StringBuilder sb = new StringBuilder(urlPath);
		sb.append('&');
		// 迭代Map
		for (Map.Entry<String, String> entry : params.entrySet()) {
			sb.append(entry.getKey()).append('=')
					.append(URLEncoder.encode(entry.getValue(), "UTF-8"))
					.append('&');
		}
		sb.deleteCharAt(sb.length() - 1);

		// 打开链接
		URL url = new URL(sb.toString());
		Log.v("test", sb.toString());
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpParams httpParams = httpClient.getParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT);
		HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT);
		HttpGet httpGet = new HttpGet(sb.toString());
		httpGet.setHeader("Accept", "application/json");
		httpGet.setHeader("Content-type", "application/json");
		String session = SharePersistent.getInstance().getPerference(context,
				"sessid");
		LogUtils.log("sessid" + session);
		if (session != null && session.length() > 0) {
			httpGet.setHeader("Cookie", "PHPSESSID=" + session);
		}

		String PHPSESSID;
		try {
			HttpResponse response = httpClient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == 200) {
				CookieStore mCookieStore = httpClient.getCookieStore();
				List<Cookie> cookies = mCookieStore.getCookies();
				for (int i = 0; i < cookies.size(); i++) {
					LogUtils.v("test", cookies.get(i).getName() + "    "
							+ cookies.get(i).getValue());
					// 这里是读取Cookie['PHPSESSID']的值存在静态变量中，保证每次都是同一个值
					if ("PHPSESSID".equals(cookies.get(i).getName())) {
						PHPSESSID = cookies.get(i).getValue();
						if (PHPSESSID != null) {
							SharePersistent.getInstance().savePerference(
									context, "sessid", PHPSESSID);
						}
						LogUtils.v("test", "sessinid " + PHPSESSID);
						break;
					}
				}

				HttpEntity entity = response.getEntity();
				String body = EntityUtils.toString(entity);
				return body;
			}else {
				LogUtils.log("错误数据 "+ EntityUtils.toString(response.getEntity()));
			}

		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "sendGetRequest error!";
	}



	/**
	 * 通过Post方式提交参数给服务器,也可以用来传送json或xml文件
	 * 
	 * @param urlPath
	 * @param params
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public static String sendPostRequest(String urlPath,
			Map<String, String> params) throws Exception {
		DefaultHttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, TIMEOUT);
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
				TIMEOUT);
		HttpPost httpPost = new HttpPost(urlPath);
		ArrayList<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
		if (params != null) {
			Set<String> keys = params.keySet();
			for (Iterator<String> i = keys.iterator(); i.hasNext();) {
				String key = i.next();
				pairs.add(new BasicNameValuePair(key, params.get(key)));
			}
		}

		try {
			UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(pairs,
					"UTF-8");
			httpPost.setEntity(p_entity);
			HttpResponse response = client.execute(httpPost);
			HttpEntity entity = response.getEntity();
			String body = EntityUtils.toString(entity);
			return body;
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "sendText error!";
	}

	/**
	 * 在遇上HTTPS安全模式或者操作cookie的时候使用HTTPclient会方便很多 使用HTTPClient（开源项目）向服务器提交参数
	 * 
	 * @param urlPath
	 * @param params
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public static String sendHttpClientPost(Context context,String urlPath,
			Map<String, String> params) throws Exception {
		// 需要把参数放到NameValuePair
		List<NameValuePair> paramPairs = new ArrayList<NameValuePair>();
		if (params != null && !params.isEmpty()) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				paramPairs.add(new BasicNameValuePair(entry.getKey(), entry
						.getValue()));

			}
		}
		// 对请求参数进行编码，得到实体数据
		UrlEncodedFormEntity entitydata = new UrlEncodedFormEntity(paramPairs,
				"UTF-8");
		// 构造一个请求路径
		HttpPost post = new HttpPost(urlPath);
		String session = SharePersistent.getInstance().getPerference(context,
				"sessid");
		LogUtils.log("sessid" + session);
		if (session != null && session.length() > 0) {
			post.setHeader("Cookie", "PHPSESSID=" + session);
		}
		// 设置请求实体
		post.setEntity(entitydata);
		// 浏览器对象
		DefaultHttpClient client = new DefaultHttpClient();
		
		// 执行post请求
		HttpResponse response = client.execute(post);
		// 从状态行中获取状态码，判断响应码是否符合要求
		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = response.getEntity();
			InputStream inputStream = entity.getContent();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "UTF-8");
			BufferedReader reader = new BufferedReader(inputStreamReader);// 读字符串用的。
			String s;
			String responseData = "";
			while (((s = reader.readLine()) != null)) {
				responseData += s;
			}
			reader.close();// 关闭输入流
			return responseData;
		}
		return "sendHttpClientPost error!";
	}

	

	/**
	 * 根据URL直接读文件内容，前提是这个文件当中的内容是文本，函数的返回值就是文件当中的内容
	 * 
	 * @param urlStr
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public static String readTxtFile(String urlStr, String encoding)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		String line = null;
		BufferedReader buffer = null;
		try {
			// 创建一个URL对象
			URL url = new URL(urlStr);
			// 创建一个Http连接
			HttpURLConnection urlConn = (HttpURLConnection) url
					.openConnection();
			// 使用IO流读取数据
			buffer = new BufferedReader(new InputStreamReader(
					urlConn.getInputStream(), encoding));
			while ((line = buffer.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				buffer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	/**
	 * 该函数返回整形 -1：代表下载文件出错 0：代表下载文件成功 1：代表文件已经存在
	 * 
	 * @param urlStr
	 * @param path
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static int downloadFile(String urlStr, String path, String fileName)
			throws Exception {
		InputStream inputStream = null;
		try {
			inputStream = getInputStreamFromUrl(urlStr);
			File resultFile = write2SDFromInput(path, fileName, inputStream);
			if (resultFile == null) {
				return -1;
			}

		} catch (Exception e) {
			return -1;
		} finally {
			try {
				inputStream.close();
			} catch (Exception e) {
				throw e;
			}
		}
		return 0;
	}

	/**
	 * 根据URL得到输入流
	 * 
	 * @param urlStr
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static InputStream getInputStreamFromUrl(String urlStr)
			throws MalformedURLException, IOException {
		URL url = new URL(urlStr);
		HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
		InputStream inputStream = urlConn.getInputStream();
		return inputStream;
	}

	/**
	 * 将一个InputStream里面的数据写入到SD卡中
	 * 
	 * @param directory
	 * @param fileName
	 * @param input
	 * @return
	 */
	private static File write2SDFromInput(String directory, String fileName,
			InputStream input) {
		File file = null;
		String SDPATH = Environment.getExternalStorageDirectory().toString();
		FileOutputStream output = null;
		File dir = new File(SDPATH + directory);
		if (!dir.exists()) {
			dir.mkdir();
		}
		try {
			file = new File(dir + File.separator + fileName);
			file.createNewFile();
			output = new FileOutputStream(file);
			byte buffer[] = new byte[1024];
			while ((input.read(buffer)) != -1) {
				output.write(buffer);
			}
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}
}
