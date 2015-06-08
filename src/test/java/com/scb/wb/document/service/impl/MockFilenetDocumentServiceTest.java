package com.scb.wb.document.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.test.util.ReflectionTestUtils;

import com.scb.wb.document.exception.DocumentException;
import com.scb.wb.document.model.FilenetDocumentRequest;
import com.scb.wb.document.service.FilenetDocumentService;
import com.scb.wb.document.service.mock.impl.MockFilenetDocumentServiceImpl;

public class MockFilenetDocumentServiceTest {

	@InjectMocks
	private final FilenetDocumentService filenetDocumentService = new MockFilenetDocumentServiceImpl();

	@Test
	@Ignore
	public void uploadDocumentAllValidDataSuccess() throws DocumentException {
		final FilenetDocumentRequest filenetDocumentRequest = mock(FilenetDocumentRequest.class);
		ReflectionTestUtils.setField(filenetDocumentService, "csURL", "http://10.128.76.162:9082/ContentServices");
		ReflectionTestUtils.setField(filenetDocumentService, "consumerKey", "oneWBCSUser");
		ReflectionTestUtils.setField(filenetDocumentService, "consumerSecret", "oneWBCSPass");
		ReflectionTestUtils.setField(filenetDocumentService, "bankId", "1436621");
		ReflectionTestUtils.setField(filenetDocumentService, "documentClass", "OneWBWorkBench");
		ReflectionTestUtils.setField(filenetDocumentService, "objectStore", "OneWB");
		ReflectionTestUtils.setField(filenetDocumentService, "location", "HK");

		final String documentReferenceId = filenetDocumentService.uploadDocument(filenetDocumentRequest);
		assertNotNull("uploadDocument should not be null", documentReferenceId);
	}

	@Test
	@Ignore
	public void overwriteDocumentAllValidDataSuccess() throws DocumentException {
		final FilenetDocumentRequest filenetDocumentRequest = mock(FilenetDocumentRequest.class);
		final String documentReferenceId = filenetDocumentService.overwriteDocument(filenetDocumentRequest);
		assertNotNull("uploadDocument should not be null", documentReferenceId);
	}

}
