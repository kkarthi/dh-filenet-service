package com.scb.wb.filenet.service.mock.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;

import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scb.cs.client.CS;
import com.scb.exception.CSException;
import com.scb.jsonpojo.CSDocument;

/**
 * 
 * This class is a Mock Data setup for Content Service.
 *
 */
public class CSMockSetup {

	private final static Logger LOGGER = LoggerFactory.getLogger(CSMockSetup.class);
	private static final String URL = "http://localhost:8080/file";

	public CS getContentServiceRequestToken() throws CSException {
		final CS contentService = mock(CS.class);
		stub(contentService.requestToken(any(String.class), any(String.class), any(String.class))).toAnswer(
				new Answer<Object>() {
					@Override
					public Object answer(final InvocationOnMock invocation) throws CSException {
						validateRequestToken(invocation);
						LOGGER.debug("Generating Token");
						final Map<String, String> tokens = new HashMap<String, String>();
						tokens.put("requestToken", "123456");
						tokens.put("tokenSecret", "123456");
						return tokens;
					}

					private void validateRequestToken(final InvocationOnMock invocation) throws CSException {
						final Object[] args = invocation.getArguments();
						final String consumerKey = (String) args[0];
						final String consumerSecret = (String) args[1];
						final String bankId = (String) args[2];

						if (StringUtils.isBlank(consumerKey) || StringUtils.isBlank(consumerSecret)
								|| StringUtils.isBlank(bankId)) {
							throw new CSException("requestToken#emptyValues");
						}
					}
				});
		return contentService;
	}

	@SuppressWarnings("unchecked")
	public CS getContentServiceAddDocument() throws CSException {
		final CS contentService = mock(CS.class);
		stub(
				contentService.addDocument(any(String.class), any(String.class), any(String.class), any(HashMap.class),
						any(InputStream.class), any(String.class), any(String.class), any(String.class),
						any(String.class), any(String.class))).toAnswer(new Answer<Object>() {
			@Override
			public Object answer(final InvocationOnMock invocation) throws CSException {
				validateAddDocument(invocation);
				return UUID.randomUUID().toString();
			}

			private void validateAddDocument(final InvocationOnMock invocation) throws CSException {
				final Object[] args = invocation.getArguments();

				final String requestToken = (String) args[0];
				final String tokenSecret = (String) args[1];
				final String consumerSecret = (String) args[2];
				final HashMap<String, String> propertiesValues = (HashMap<String, String>) args[3];
				final String fileName = (String) args[5];
				final String osName = (String) args[7];
				final String docClassName = (String) args[8];

				if (StringUtils.isBlank(requestToken) || StringUtils.isBlank(tokenSecret)
						|| StringUtils.isBlank(consumerSecret) || StringUtils.isBlank(fileName)
						|| StringUtils.isBlank(osName) || StringUtils.isBlank(docClassName)
						|| propertiesValues.size() == 0) {
					throw new CSException("addDocument#emptyValues");
				}
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
						return UUID.randomUUID().toString();
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