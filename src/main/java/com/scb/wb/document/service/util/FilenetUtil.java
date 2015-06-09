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
		propertiesValues.put("DocCategory", filenetDocumentRequest.getDocCategory());
		propertiesValues.put("DocID", filenetDocumentRequest.getDocumentId());
		propertiesValues.put("ProductoftheDeal", filenetDocumentRequest.getDealId());
		propertiesValues.put("UserFullName", filenetDocumentRequest.getUserFullName());
		propertiesValues.put("DateTimeofUpload", System.currentTimeMillis() + "");
		propertiesValues.put("DealID", filenetDocumentRequest.getDealId());
		propertiesValues.put("ClientIDorProspectID", filenetDocumentRequest.getDealId());
		propertiesValues.put("SubProductoftheDeal", filenetDocumentRequest.getDealId());
		return propertiesValues;
	}

}
