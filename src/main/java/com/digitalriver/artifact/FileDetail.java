package com.digitalriver.artifact;

import java.io.Serializable;

public class FileDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	private String fileName;
	private long length;
	private String modifiedDate;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

}
