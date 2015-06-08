package com.scb.wb.document.service;

import com.scb.wb.document.exception.DocumentException;
import com.scb.wb.document.model.FilenetDocumentRequest;

/**
 * 
 * This {@code FilenetDocumentService} used to interact with the File-net Content Service.
 *
 */
public interface FilenetDocumentService {

	String uploadDocument(FilenetDocumentRequest filenetDocumentRequest) throws DocumentException;

	String overwriteDocument(FilenetDocumentRequest filenetDocumentRequest) throws DocumentException;

	String getDocumentDownloadUrl(FilenetDocumentRequest filenetDocumentRequest) throws DocumentException;

	boolean deleteDocument(FilenetDocumentRequest filenetDocumentRequest) throws DocumentException;

}
