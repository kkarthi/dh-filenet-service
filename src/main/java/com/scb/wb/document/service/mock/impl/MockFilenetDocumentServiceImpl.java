package com.scb.wb.document.service.mock.impl;

import java.util.LinkedList;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.scb.cs.client.CS;
import com.scb.jsonpojo.CSDocument;
import com.scb.wb.document.condition.FilenetImplementationConfiguration.FilenetProerties;
import com.scb.wb.document.exception.DocumentException;
import com.scb.wb.document.model.FilenetDocumentRequest;
import com.scb.wb.document.service.FilenetDocumentService;
import com.scb.wb.filenet.service.mock.impl.CSMockSetup;

/**
 * 
 * @see FilenetDocumentService
 *
 */
@Service("mockFilenetDocumentServiceImpl")
@PropertySource("classpath:document-filenet.properties")
public class MockFilenetDocumentServiceImpl implements FilenetDocumentService {

	private final static Logger LOGGER = LoggerFactory.getLogger(MockFilenetDocumentServiceImpl.class);

	private static final String TOKEN_SECRET = "tokenSecret";
	private static final String REQUEST_TOKEN = "requestToken";

	@Autowired
	private FilenetProerties properties;

	@Override
	public String uploadDocument(final FilenetDocumentRequest filenetDocumentRequest) throws DocumentException {
		final Map<String, String> filenetProperties = getFilenetProperties(filenetDocumentRequest);

		final String consumerSecret = filenetProperties.get("document.filenet.consumerSecret");
		final String objectStore = filenetProperties.get("document.filenet.objectStore");
		final String documentClass = filenetProperties.get("document.filenet.documentClass");

		final Map<String, String> tokens = requestToken(filenetDocumentRequest);
		try {
			final CS contentServiceAdd = new CSMockSetup().getContentServiceAddDocument();
			final String documentReferenceID = contentServiceAdd.addDocument(tokens.get(REQUEST_TOKEN),
					tokens.get(TOKEN_SECRET), consumerSecret, filenetDocumentRequest.getFilenetMetadata(),
					filenetDocumentRequest.getDocumentStream(), filenetDocumentRequest.getDocumentName(), null,
					objectStore, documentClass, null);
			LOGGER.info("Document has been uploaded successfully, The Document Reference ID is " + documentReferenceID);
			return documentReferenceID;
		} catch (final Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new DocumentException(e.getMessage(), e);
		}
	}

	@Override
	public String overwriteDocument(final FilenetDocumentRequest filenetDocumentRequest) throws DocumentException {
		final Map<String, String> filenetProperties = getFilenetProperties(filenetDocumentRequest);

		final String consumerSecret = filenetProperties.get("document.filenet.consumerSecret");
		final String objectStore = filenetProperties.get("document.filenet.objectStore");
		final String folderPath = filenetProperties.get("document.filenet.folderPath");

		final Map<String, String> tokens = requestToken(filenetDocumentRequest);

		try {
			final CS contentServiceUpdate = new CSMockSetup().getContentServiceOvereriteDocument();
			final String documentReferenceID = contentServiceUpdate.versionDocument(tokens.get(REQUEST_TOKEN),
					tokens.get(TOKEN_SECRET), consumerSecret, objectStore, filenetDocumentRequest.getDocumentId(),
					filenetDocumentRequest.getDocumentStream(), null, filenetDocumentRequest.getDocumentName(),
					folderPath, filenetDocumentRequest.getFilenetMetadata(), false);
			LOGGER.info("Document has been updated successfully, The Document Reference ID is " + documentReferenceID);
			return documentReferenceID;
		} catch (final Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new DocumentException(e.getMessage(), e);
		}
	}

	@Override
	public String getDocumentDownloadUrl(final FilenetDocumentRequest filenetDocumentRequest) throws DocumentException {
		final Map<String, String> filenetProperties = getFilenetProperties(filenetDocumentRequest);

		final String consumerSecret = filenetProperties.get("document.filenet.consumerSecret");
		final String objectStore = filenetProperties.get("document.filenet.objectStore");
		final String documentClass = filenetProperties.get("document.filenet.documentClass");

		final Map<String, String> tokens = requestToken(filenetDocumentRequest);
		try {
			final String query = "SELECT Id, DocumentTitle,ContentSize, DateCreated, MIMEType FROM " + documentClass
					+ " WHERE Id = " + filenetDocumentRequest.getFilenetReferenceId();
			final CS contentServiceDocumentsListFromQuery = new CSMockSetup().getContentServiceDocumentsListFromQuery();
			final String location = (filenetDocumentRequest.getDocumentLocation() == null) ? filenetProperties
					.get("document.filenet.location") : filenetDocumentRequest.getDocumentLocation();
			final LinkedList<CSDocument> csDocuments = contentServiceDocumentsListFromQuery.getDocumentsListFromQuery(
					tokens.get(REQUEST_TOKEN), tokens.get(TOKEN_SECRET), consumerSecret, objectStore, query, location);
			if (CollectionUtils.isNotEmpty(csDocuments)) {
				final CSDocument csDocument = csDocuments.get(0);
				final CS contentServiceDocumentContent = new CSMockSetup().getContentServiceDocumentContent();
				return contentServiceDocumentContent
						.getDocumentContent(tokens.get(REQUEST_TOKEN), tokens.get(TOKEN_SECRET), consumerSecret,
								objectStore, true, false, csDocument.getContentLink());
			}
		} catch (final Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new DocumentException(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public boolean deleteDocument(final FilenetDocumentRequest filenetDocumentRequest) throws DocumentException {
		final Map<String, String> filenetProperties = getFilenetProperties(filenetDocumentRequest);

		final String consumerSecret = filenetProperties.get("document.filenet.consumerSecret");
		final String objectStore = filenetProperties.get("document.filenet.objectStore");

		final Map<String, String> tokens = requestToken(filenetDocumentRequest);
		try {
			final CS contentServiceDelete = new CSMockSetup().getContentServiceDeleteDocument();
			return contentServiceDelete.deleteDocument(tokens.get(REQUEST_TOKEN), tokens.get(TOKEN_SECRET),
					consumerSecret, objectStore, filenetDocumentRequest.getDocumentId());
		} catch (final Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new DocumentException(e.getMessage(), e);
		}
	}

	private Map<String, String> requestToken(final FilenetDocumentRequest filenetDocumentRequest)
			throws DocumentException {
		final Map<String, String> filenetProperties = getFilenetProperties(filenetDocumentRequest);
		final String consumerKey = filenetProperties.get("document.filenet.consumerKey");
		final String consumerSecret = filenetProperties.get("document.filenet.consumerSecret");
		final String bankId = filenetProperties.get("document.filenet.bankId");

		try {
			final CS contentService = new CSMockSetup().getContentServiceRequestToken();
			final Map<String, String> tokens = contentService.requestToken(consumerKey, consumerSecret, bankId);
			LOGGER.debug("requestToken : " + tokens.get(REQUEST_TOKEN) + " tokenSecret : " + tokens.get(TOKEN_SECRET));
			return tokens;
		} catch (final Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new DocumentException(e.getMessage(), e);
		}
	}

	private Map<String, String> getFilenetProperties(final FilenetDocumentRequest filenetDocumentRequest) {
		final Map<String, Map<String, String>> filenetProerties = properties.getFilenetProerties();
		return filenetProerties.get(filenetDocumentRequest.getAppGroup());
	}
}