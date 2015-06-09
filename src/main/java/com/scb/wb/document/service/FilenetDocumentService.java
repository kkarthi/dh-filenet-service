package com.scb.wb.document.service;

import com.scb.wb.document.exception.DocumentException;
import com.scb.wb.document.model.FilenetDocumentRequest;

/**
 * 
 * This {@code FilenetDocumentService} used to interact with the File-net Content Service.
 *
 */
public interface FilenetDocumentService {

	/**
	 * This method used to upload the document into file-net server.
	 * 
	 * @param filenetDocumentRequest
	 *            contains the content of the document, name and it's properties.
	 * @return Unique file-net reference id.
	 * @throws DocumentException
	 */
	String uploadDocument(FilenetDocumentRequest filenetDocumentRequest) throws DocumentException;

	/**
	 * This method used to update the document into file-net server.
	 * 
	 * @param filenetDocumentRequest
	 *            contains the content of the document, name and it's properties.
	 * @return Unique file-net reference id.
	 * @throws DocumentException
	 */
	String overwriteDocument(FilenetDocumentRequest filenetDocumentRequest) throws DocumentException;

	/**
	 * This method used to download the content of the document.
	 * 
	 * @param filenetDocumentRequest
	 *            contains the file-net reference id and it's properties.
	 * @return URL of the file.
	 * @throws DocumentException
	 */
	String getDocumentDownloadUrl(FilenetDocumentRequest filenetDocumentRequest) throws DocumentException;

	/**
	 * This method used to download the content of the document.
	 * 
	 * @param filenetDocumentRequest
	 *            contains the file-net reference id and it's properties.
	 * @return whether the file has been deleted or not.
	 * @throws DocumentException
	 */
	boolean deleteDocument(FilenetDocumentRequest filenetDocumentRequest) throws DocumentException;
}
