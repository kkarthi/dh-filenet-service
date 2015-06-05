package com.scb.wb.document.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.mockito.InjectMocks;

import com.scb.wb.document.model.FilenetDocumentRequest;
import com.scb.wb.document.service.FilenetDocumentService;
import com.scb.wb.document.service.mock.impl.MockFilenetDocumentServiceImpl;

public class MockFilenetDocumentServiceTest {

	@InjectMocks
	private final FilenetDocumentService filenetDocumentService = new MockFilenetDocumentServiceImpl();

	@Test
	public void uploadDocumentAllValidDataSuccess() {
		final FilenetDocumentRequest filenetDocumentRequest = mock(FilenetDocumentRequest.class);
		final String documentReferenceId = filenetDocumentService.uploadDocument(filenetDocumentRequest);
		assertNotNull("uploadDocument should not be null", documentReferenceId);
	}

	@Test
	public void overwriteDocumentAllValidDataSuccess() {
		final FilenetDocumentRequest filenetDocumentRequest = mock(FilenetDocumentRequest.class);
		final String documentReferenceId = filenetDocumentService.overwriteDocument(filenetDocumentRequest);
		assertNotNull("uploadDocument should not be null", documentReferenceId);
	}

}
