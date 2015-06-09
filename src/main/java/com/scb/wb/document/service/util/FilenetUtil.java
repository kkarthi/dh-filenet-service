package com.scb.wb.document.service.util;

import java.util.HashMap;

import javax.activation.MimetypesFileTypeMap;

import org.apache.commons.io.FilenameUtils;

import com.scb.wb.document.model.FilenetDocumentRequest;

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

	public static HashMap<String, String> getDocumentProperties(final FilenetDocumentRequest filenetDocumentRequest) {

		final HashMap<String, String> propertiesValues = new HashMap<String, String>();

		propertiesValues.put("DocumentTitle", filenetDocumentRequest.getDocumentName());
		propertiesValues.put("DocumentName", filenetDocumentRequest.getDocumentName());
		propertiesValues.put("DocumentType", FilenameUtils.getExtension(filenetDocumentRequest.getDocumentName()));
		propertiesValues.put("Country", filenetDocumentRequest.getDocumentLocation());
		setDocumentCategory(propertiesValues);
		// TODO : Fix Me!
		propertiesValues.put("DocID", "");
		propertiesValues.put("ProductoftheDeal", "");
		propertiesValues.put("UserFullName", "");
		propertiesValues.put("DateTimeofUpload", System.currentTimeMillis() + "");
		propertiesValues.put("DealID", "");
		propertiesValues.put("ClientIDorProspectID", "");
		propertiesValues.put("SubProductoftheDeal", "");
		return propertiesValues;
	}

	private static void setDocumentCategory(final HashMap<String, String> propertiesValues) {
		propertiesValues.put("DocCategory", "Transaction Documents");
	}

}
