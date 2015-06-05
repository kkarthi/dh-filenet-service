package com.scb.wb.document.service.impl;

import org.springframework.stereotype.Service;

import com.scb.wb.document.model.FilenetDocumentRequest;
import com.scb.wb.document.service.FilenetDocumentService;

/**
 * 
 * @see FilenetDocumentService
 *
 */
@Service
public class FilenetDocumentServiceImpl implements FilenetDocumentService {

	@Override
	public String uploadDocument(final FilenetDocumentRequest filenetDocumentRequest) {
		return null;
	}

	@Override
	public String overwriteDocument(final FilenetDocumentRequest filenetDocumentRequest) {
		return null;
	}

	@Override
	public String getDocumentDownloadUrl(final FilenetDocumentRequest filenetDocumentRequest) {
		return null;
	}

	@Override
	public boolean deleteDocument(final FilenetDocumentRequest filenetDocumentRequest) {
		return false;
	}
}
