package com.scb.wb.document.service.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.scb.cs.client.CS;
import com.scb.jsonpojo.CSDocument;
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
	public String uploadDocument(final FilenetDocumentRequest filenetDocumentRequest) throws DocumentException {
		try {
			final CS contentService = new CS(csURL);
			final Map<String, String> tokens = contentService.requestToken(consumerKey, consumerSecret, bankId);
			final String requestToken = tokens.get(REQUEST_TOKEN);
			final String tokenSecret = tokens.get(TOKEN_SECRET);
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
		try {
			final CS contentService = new CS(csURL);
			LOGGER.info("FILEUPLOAD: getDocumentVersion");
			final HashMap<String, String> tokens = contentService.requestToken(consumerKey, consumerSecret, bankId);
			final String requestToken = tokens.get(REQUEST_TOKEN);
			final String tokenSecret = tokens.get(TOKEN_SECRET);
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
		try {
			final CS contentService = new CS(csURL);
			final String query = "SELECT Id, DocumentTitle,ContentSize, DateCreated, MIMEType " + " FROM "
					+ documentClass + " WHERE Id = " + filenetDocumentRequest.getFilenetReferenceId();
			final HashMap<String, String> tokens = contentService.requestToken(consumerKey, consumerSecret, bankId);
			final String requestToken = tokens.get(REQUEST_TOKEN);
			final String tokenSecret = tokens.get(TOKEN_SECRET);
			final String documentLocation = (filenetDocumentRequest.getDocumentLocation() == null) ? location
					: filenetDocumentRequest.getDocumentLocation();
			final LinkedList<CSDocument> csdocs = contentService.getDocumentsListFromQuery(requestToken, tokenSecret,
					consumerSecret, objectStore, query, documentLocation);
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
		try {
			final CS contentService = new CS(csURL);
			final HashMap<String, String> tokens = contentService.requestToken(consumerKey, consumerSecret, bankId);
			final String requestToken = tokens.get(REQUEST_TOKEN);
			final String tokenSecret = tokens.get(TOKEN_SECRET);
			return contentService.deleteDocument(requestToken, tokenSecret, consumerSecret, objectStore,
					filenetDocumentRequest.getFilenetReferenceId());
		} catch (final Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new DocumentException(e.getMessage(), e);
		}
	}
}
