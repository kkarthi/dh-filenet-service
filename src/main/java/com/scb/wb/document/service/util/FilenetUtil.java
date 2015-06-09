package com.scb.wb.document.service.util;

import javax.activation.MimetypesFileTypeMap;

/**
 * This class used for construct the File-net related request.
 */
public final class FilenetUtil {

	private static MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();

	static {
		mimetypesFileTypeMap.addMimeTypes("application/pdf pdf");
		mimetypesFileTypeMap.addMimeTypes("application/zip zip");
		mimetypesFileTypeMap.addMimeTypes("text/plain txt");
		mimetypesFileTypeMap.addMimeTypes("application/msword docx");
		mimetypesFileTypeMap.addMimeTypes("application/msword doc");
		mimetypesFileTypeMap.addMimeTypes("application/vnd.ms-excel xls");
		mimetypesFileTypeMap.addMimeTypes("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet xlsx");
		mimetypesFileTypeMap.addMimeTypes("application/vnd.ms-powerpoint ppt");
		mimetypesFileTypeMap.addMimeTypes("application/vnd.ms-powerpoint pptx");
		mimetypesFileTypeMap.addMimeTypes("application/vnd.ms-outlook msg");
		mimetypesFileTypeMap.addMimeTypes("application/vnd.ms-project mpp");
		mimetypesFileTypeMap.addMimeTypes("image/tiff tiff");
		mimetypesFileTypeMap.addMimeTypes("image/tiff tif");
	}

	public static String getMimeType(final String fileName) {
		return mimetypesFileTypeMap.getContentType(fileName);
	}
}
