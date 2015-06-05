package com.scb.wb.document.service.mock.impl;

import java.util.LinkedList;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.scb.cs.client.CS;
import com.scb.exception.CSException;
import com.scb.jsonpojo.CSDocument;
import com.scb.wb.document.model.FilenetDocumentRequest;
import com.scb.wb.document.service.FilenetDocumentService;
import com.scb.wb.filenet.service.impl.CSMockSetup;

/**
 * 
 * @see FilenetDocumentService
 *
 */
@Service
@PropertySource("classpath:document-filenet.properties")
public class MockFilenetDocumentServiceImpl implements FilenetDocumentService {

	private final static Logger LOGGER = LoggerFactory.getLogger(MockFilenetDocumentServiceImpl.class);

	private static final String TOKEN_SECRET = "tokenSecret";
	private static final String REQUEST_TOKEN = "requestToken";

	@Value("${document.filenet.csURL}")
	private String csURL;

	@Value("${document.filenet.consumerKey}")
	private String consumerKey;

	@Value("${document.filenet.consumerSecret}")
	private String consumerSecret;

	@Value("${document.filenet.bankId}")
	private String bankId;

	@Value("${document.filenet.documentClass}")
	private String documentClass;

	@Value("${document.filenet.objectStore}")
	private String objectStore;

	@Value("${document.filenet.folderPath}")
	private String folderPath;

	@Value("${document.filenet.location}")
	private String location;

	@Override
	public String uploadDocument(final FilenetDocumentRequest filenetDocumentRequest) {
		final Map<String, String> tokens = requestToken();
		try {
			final CS contentServiceAdd = new CSMockSetup().getContentServiceAddDocument();
			final String documentReferenceID = contentServiceAdd.addDocument(tokens.get(REQUEST_TOKEN),
					tokens.get(TOKEN_SECRET), consumerSecret, filenetDocumentRequest.getFilenetMetadata(),
					filenetDocumentRequest.getDocumentStream(), filenetDocumentRequest.getDocumentName(), null,
					objectStore, documentClass, null);
			LOGGER.info("Document has been uploaded successfully, The Document Reference ID is " + documentReferenceID);
			return documentReferenceID;
		} catch (final CSException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String overwriteDocument(final FilenetDocumentRequest filenetDocumentRequest) {
		final Map<String, String> tokens = requestToken();
		try {
			final CS contentServiceUpdate = new CSMockSetup().getContentServiceOvereriteDocument();
			final String documentReferenceID = contentServiceUpdate.versionDocument(tokens.get(REQUEST_TOKEN),
					tokens.get(TOKEN_SECRET), consumerSecret, objectStore, filenetDocumentRequest.getDocumentId(),
					filenetDocumentRequest.getDocumentStream(), null, filenetDocumentRequest.getDocumentName(),
					folderPath, filenetDocumentRequest.getFilenetMetadata(), false);
			LOGGER.info("Document has been updated successfully, The Document Reference ID is " + documentReferenceID);
			return documentReferenceID;
		} catch (final CSException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getDocumentDownloadUrl(final FilenetDocumentRequest filenetDocumentRequest) {
		final Map<String, String> tokens = requestToken();
		try {
			final String query = "SELECT Id, DocumentTitle,ContentSize, DateCreated, MIMEType FROM " + documentClass
					+ " WHERE Id = " + filenetDocumentRequest.getFilenetReferenceId();
			final CS contentServiceDocumentsListFromQuery = new CSMockSetup().getContentServiceDocumentsListFromQuery();
			final LinkedList<CSDocument> csDocuments = contentServiceDocumentsListFromQuery.getDocumentsListFromQuery(
					tokens.get(REQUEST_TOKEN),
					tokens.get(TOKEN_SECRET),
					consumerSecret,
					objectStore,
					query,
					(filenetDocumentRequest.getDocumentLocation() == null) ? location : filenetDocumentRequest
							.getDocumentLocation());
			if (!csDocuments.isEmpty()) {
				final CSDocument csDocument = csDocuments.get(0);
				final CS contentServiceDocumentContent = new CSMockSetup().getContentServiceDocumentContent();
				return contentServiceDocumentContent
						.getDocumentContent(tokens.get(REQUEST_TOKEN), tokens.get(TOKEN_SECRET), consumerSecret,
								objectStore, true, false, csDocument.getContentLink());
			}
		} catch (final CSException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean deleteDocument(final FilenetDocumentRequest filenetDocumentRequest) {
		final Map<String, String> tokens = requestToken();
		try {
			final CS contentServiceDelete = new CSMockSetup().getContentServiceDeleteDocument();
			return contentServiceDelete.deleteDocument(tokens.get(REQUEST_TOKEN), tokens.get(TOKEN_SECRET),
					consumerSecret, objectStore, filenetDocumentRequest.getDocumentId());
		} catch (final CSException e) {
			e.printStackTrace();
		}
		return false;
	}

	private Map<String, String> requestToken() {
		try {
			final CS contentService = new CSMockSetup().getContentServiceRequestToken();
			final Map<String, String> tokens = contentService.requestToken(consumerKey, consumerSecret, bankId);
			LOGGER.debug("requestToken : " + tokens.get(REQUEST_TOKEN) + " tokenSecret : " + tokens.get(TOKEN_SECRET));
			return tokens;
		} catch (final CSException e) {
			e.printStackTrace();
		}
		return null;
	}
}
