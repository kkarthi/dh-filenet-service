package com.scb.wb.filenet.service.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;

import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scb.cs.client.CS;
import com.scb.exception.CSException;
import com.scb.jsonpojo.CSDocument;

public class CSMockSetup {

	private static final String URL = "http://localhost:8080/file";
	private final static Logger LOGGER = LoggerFactory.getLogger(CSMockSetup.class);

	public CS getContentServiceRequestToken() throws CSException {
		final CS contentService = mock(CS.class);
		stub(contentService.requestToken(any(String.class), any(String.class), any(String.class))).toAnswer(
				new Answer<Object>() {
					@SuppressWarnings("unused")
					@Override
					public Object answer(final InvocationOnMock invocation) {
						final Object[] args = invocation.getArguments();
						// TODO: Handle null checks
						final String consumerKey = (String) args[0];
						final String consumerSecret = (String) args[1];
						final String bankId = (String) args[2];

						LOGGER.debug("Generating Token");
						final Map<String, String> tokens = new HashMap<String, String>();
						tokens.put("requestToken", "123456");
						tokens.put("tokenSecret", "123456");
						return tokens;
					}
				});
		return contentService;
	}

	@SuppressWarnings("unchecked")
	public CS getContentServiceAddDocument() throws CSException {
		final CS contentService = mock(CS.class);
		// TODO: Handle null checks
		stub(
				contentService.addDocument(any(String.class), any(String.class), any(String.class), any(HashMap.class),
						any(InputStream.class), any(String.class), any(String.class), any(String.class),
						any(String.class), any(String.class))).toAnswer(new Answer<Object>() {
			@Override
			public Object answer(final InvocationOnMock invocation) {
				return "1234567890987654321";
			}
		});
		return contentService;
	}

	@SuppressWarnings("unchecked")
	public CS getContentServiceOvereriteDocument() throws CSException {
		final CS contentService = mock(CS.class);
		// TODO: Handle null checks
		stub(
				contentService.versionDocument(any(String.class), any(String.class), any(String.class),
						any(String.class), any(String.class), any(InputStream.class), any(String.class),
						any(String.class), any(String.class), any(HashMap.class), any(Boolean.class))).toAnswer(
				new Answer<Object>() {
					@Override
					public Object answer(final InvocationOnMock invocation) {
						return "1234567890987654321";
					}
				});
		return contentService;
	}

	public CS getContentServiceDeleteDocument() throws CSException {
		final CS contentService = mock(CS.class);
		// TODO: Handle null checks
		stub(
				contentService.deleteDocument(any(String.class), any(String.class), any(String.class),
						any(String.class), any(String.class))).toAnswer(new Answer<Object>() {
			@Override
			public Object answer(final InvocationOnMock invocation) {
				return true;
			}
		});
		return contentService;
	}

	public CS getContentServiceDocumentsListFromQuery() throws CSException {
		final CS contentService = mock(CS.class);
		// TODO: Handle null checks
		stub(
				contentService.getDocumentsListFromQuery(any(String.class), any(String.class), any(String.class),
						any(String.class), any(String.class), any(String.class))).toAnswer(new Answer<Object>() {
			@Override
			public Object answer(final InvocationOnMock invocation) {
				final LinkedList<CSDocument> csDocuments = new LinkedList<CSDocument>();
				final CSDocument csDocument = new CSDocument();
				csDocument.setContentLink(URL);
				csDocuments.add(csDocument);
				return csDocuments;
			}
		});
		return contentService;
	}

	public CS getContentServiceDocumentContent() throws CSException {
		final CS contentService = mock(CS.class);
		// TODO: Handle null checks
		stub(
				contentService.getDocumentContent(any(String.class), any(String.class), any(String.class),
						any(String.class), any(Boolean.class), any(Boolean.class), any(String.class))).toAnswer(
				new Answer<Object>() {
					@Override
					public Object answer(final InvocationOnMock invocation) {
						return URL;
					}
				});
		return contentService;
	}
}
