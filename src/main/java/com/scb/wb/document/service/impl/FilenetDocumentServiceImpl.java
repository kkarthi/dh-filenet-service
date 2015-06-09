package com.scb.wb.document.service.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scb.cs.client.CS;
import com.scb.exception.CSException;
import com.scb.jsonpojo.CSDocument;
import com.scb.wb.document.condition.FilenetImplementationConfiguration.FilenetProerties;
import com.scb.wb.document.exception.DocumentException;
import com.scb.wb.document.model.FilenetDocumentRequest;
import com.scb.wb.document.service.FilenetDocumentService;
import com.scb.wb.document.service.util.FilenetUtil;

/**
 * 
 * @see FilenetDocumentService
 *
 */
// TODO: FIX ME! Token need to be reused.
@Service("filenetDocumentServiceImpl")
public class FilenetDocumentServiceImpl implements FilenetDocumentService {

	private final static Logger LOGGER = LoggerFactory.getLogger(FilenetDocumentServiceImpl.class);

	private static final String TOKEN_SECRET = "tokenSecret";
	private static final String REQUEST_TOKEN = "requestToken";

	@Autowired
	private FilenetProerties properties;

	@Override
	public String uploadDocument(final FilenetDocumentRequest filenetDocumentRequest) throws DocumentException {
		final Map<String, String> filenetProperties = getFilenetProperties(filenetDocumentRequest);

		final String csURL = filenetProperties.get("document.filenet.csURL");
		final String consumerSecret = filenetProperties.get("document.filenet.consumerSecret");
		final String objectStore = filenetProperties.get("document.filenet.objectStore");
		final String documentClass = filenetProperties.get("document.filenet.documentClass");
		try {
			final CS contentService = new CS(csURL);
			final HashMap<String, String> token = getToken(contentService, filenetDocumentRequest);
			final String requestToken = token.get(REQUEST_TOKEN);
			final String tokenSecret = token.get(TOKEN_SECRET);
			final String documentReferenceID = contentService
					.addDocument(requestToken, tokenSecret, consumerSecret,
							filenetDocumentRequest.getFilenetMetadata(), filenetDocumentRequest.getDocumentStream(),
							filenetDocumentRequest.getDocumentName(),
							FilenetUtil.getMimeType(filenetDocumentRequest.getDocumentName()), objectStore,
							documentClass, null);
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

		final String csURL = filenetProperties.get("document.filenet.csURL");
		final String consumerSecret = filenetProperties.get("document.filenet.consumerSecret");
		final String objectStore = filenetProperties.get("document.filenet.objectStore");

		try {
			final CS contentService = new CS(csURL);
			LOGGER.info("FILEUPLOAD: getDocumentVersion");
			final HashMap<String, String> token = getToken(contentService, filenetDocumentRequest);
			final String requestToken = token.get(REQUEST_TOKEN);
			final String tokenSecret = token.get(TOKEN_SECRET);
			final String documentReferenceID = contentService.versionDocument(requestToken, tokenSecret,
					consumerSecret, objectStore, filenetDocumentRequest.getFilenetReferenceId(),
					filenetDocumentRequest.getDocumentStream(),
					FilenetUtil.getMimeType(filenetDocumentRequest.getDocumentName()),
					filenetDocumentRequest.getDocumentName(), null, filenetDocumentRequest.getFilenetMetadata(), true);
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

		final String csURL = filenetProperties.get("document.filenet.csURL");
		final String consumerSecret = filenetProperties.get("document.filenet.consumerSecret");
		final String objectStore = filenetProperties.get("document.filenet.objectStore");
		final String documentClass = filenetProperties.get("document.filenet.documentClass");

		try {
			final CS contentService = new CS(csURL);
			final String query = getDownloadQuery(filenetDocumentRequest, documentClass);
			final HashMap<String, String> token = getToken(contentService, filenetDocumentRequest);
			final String requestToken = token.get(REQUEST_TOKEN);
			final String tokenSecret = token.get(TOKEN_SECRET);
			final String location = (filenetDocumentRequest.getDocumentLocation() == null) ? filenetProperties
					.get("document.filenet.location") : filenetDocumentRequest.getDocumentLocation();
			final LinkedList<CSDocument> csdocs = contentService.getDocumentsListFromQuery(requestToken, tokenSecret,
					consumerSecret, objectStore, query, location);
			if (CollectionUtils.isNotEmpty(csdocs)) {
				final CSDocument csDocument = csdocs.get(0);
				return contentService.getDocumentContent(requestToken, tokenSecret, consumerSecret, objectStore, true,
						false, csDocument.getContentLink());
			}
			return null;
		} catch (final Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new DocumentException(e.getMessage(), e);
		}
	}

	@Override
	public boolean deleteDocument(final FilenetDocumentRequest filenetDocumentRequest) throws DocumentException {
		final Map<String, String> filenetProperties = getFilenetProperties(filenetDocumentRequest);

		final String consumerSecret = filenetProperties.get("document.filenet.consumerSecret");
		final String objectStore = filenetProperties.get("document.filenet.objectStore");

		final String csURL = filenetProperties.get("document.filenet.csURL");
		try {
			final CS contentService = new CS(csURL);
			final HashMap<String, String> token = getToken(contentService, filenetDocumentRequest);
			final String requestToken = token.get(REQUEST_TOKEN);
			final String tokenSecret = token.get(TOKEN_SECRET);
			return contentService.deleteDocument(requestToken, tokenSecret, consumerSecret, objectStore,
					filenetDocumentRequest.getFilenetReferenceId());
		} catch (final Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new DocumentException(e.getMessage(), e);
		}
	}

	private HashMap<String, String> getToken(final CS contentService,
			final FilenetDocumentRequest filenetDocumentRequest) throws CSException {
		final Map<String, String> filenetProperties = getFilenetProperties(filenetDocumentRequest);

		final String consumerKey = filenetProperties.get("document.filenet.consumerKey");
		final String consumerSecret = filenetProperties.get("document.filenet.consumerSecret");
		final String bankId = filenetProperties.get("document.filenet.bankId");

		return contentService.requestToken(consumerKey, consumerSecret, bankId);
	}

	private Map<String, String> getFilenetProperties(final FilenetDocumentRequest filenetDocumentRequest) {
		final Map<String, Map<String, String>> filenetProerties = properties.getFilenetProerties();
		return filenetProerties.get(filenetDocumentRequest.getAppGroup());
	}

	private String getDownloadQuery(final FilenetDocumentRequest filenetDocumentRequest, final String documentClass) {
		final StringBuilder builder = new StringBuilder();
		builder.append("SELECT Id, DocumentTitle, ContentSize, DateCreated, MIMEType FROM ");
		builder.append(documentClass).append(" WHERE Id = ").append(filenetDocumentRequest.getFilenetReferenceId());
		return builder.toString();
	}

}
