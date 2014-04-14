package com.wowotuan.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * �ϴ��ļ�
 */
public class FormFile {
	/* �ϴ��ļ������? */
	private byte[] data;
	private InputStream inStream;
	private File file;
	/* �ļ����? */
	private String filname;
	/* ���������� */
	private String parameterName;
	/* �������� */
	private String contentType = "application/octet-stream";

	/**
	 * �˺�����������С�ļ�
	 * 
	 * @param filname
	 * @param data
	 * @param parameterName
	 *            HTML�Ŀؼ��������?
	 * @param contentType
	 */
	public FormFile(String filname, byte[] data, String parameterName,
			String contentType) {
		this.data = data;
		this.filname = filname;
		this.parameterName = parameterName;
		if (contentType != null)
			this.contentType = contentType;
	}

	/**
	 * �˺�������������ļ�?
	 * 
	 * @param filname
	 * @param file
	 * @param parameterName
	 * @param contentType
	 */
	public FormFile(String filname, File file, String parameterName,
			String contentType) {
		this.filname = filname;
		this.parameterName = parameterName;
		this.file = file;
		try {
			this.inStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (contentType != null)
			this.contentType = contentType;
	}

	public File getFile() {
		return file;
	}

	public InputStream getInStream() {
		return inStream;
	}

	public byte[] getData() {
		return data;
	}

	public String getFilname() {
		return filname;
	}

	public void setFilname(String filname) {
		this.filname = filname;
	}

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

}
