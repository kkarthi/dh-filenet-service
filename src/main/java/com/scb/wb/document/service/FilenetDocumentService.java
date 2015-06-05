package com.scb.wb.document.service;

import com.scb.wb.document.model.FilenetDocumentRequest;

/**
 * 
 * This {@code FilenetDocumentService} used to interact with the File-net Content Service.
 *
 */
public interface FilenetDocumentService {

	String uploadDocument(FilenetDocumentRequest filenetDocumentRequest);

	String overwriteDocument(FilenetDocumentRequest filenetDocumentRequest);

	String getDocumentDownloadUrl(FilenetDocumentRequest filenetDocumentRequest);

	boolean deleteDocument(FilenetDocumentRequest filenetDocumentRequest);

}
