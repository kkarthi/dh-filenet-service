package com.scb.wb.document.model;

import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;

/**
 * 
 * This class contains the file-net related properties.
 *
 */
public class FilenetDocumentRequest implements Serializable {

	private static final long serialVersionUID = 1329394228453430607L;

	/**
	 * This is a reference id.
	 */
	private String documentId;

	/**
	 * Once the file has been uploaded successfully, File-net creates a Unique ID for the future reference and which
	 * will be used to identify the Document in the File-net server.
	 */
	private String filenetReferenceId;

	/**
	 * dealId
	 */
	private String dealId;

	/**
	 * docCategory
	 */
	private String docCategory;

	/**
	 * userFullName
	 */
	private String userFullName;

	/**
	 * This field used to define the document name.
	 */
	private String documentName;

	/**
	 * This field refers the location of the cache server.
	 */
	private String documentLocation;

	/**
	 * The actual document content.
	 */
	private InputStream documentStream;

	/**
	 * 
	 */
	private String appGroup;

	/**
	 * This field contains File-Net related meta data information.
	 */
	private final HashMap<String, String> filenetMetadata = new HashMap<String, String>();

	/**
	 * @return the documentId
	 */
	public String getDocumentId() {
		return documentId;
	}

	/**
	 * @param documentId
	 *            the documentId to set
	 */
	public void setDocumentId(final String documentId) {
		this.documentId = documentId;
	}

	/**
	 * @return the dealId
	 */
	public String getDealId() {
		return dealId;
	}

	/**
	 * @param dealId
	 *            the documentId to set
	 */
	public void setDealId(final String dealId) {
		this.dealId = dealId;
	}

	/**
	 * @return the docCategory
	 */
	public String getDocCategory() {
		return docCategory;
	}

	/**
	 * @param docCategory
	 *            the docCategory to set
	 */
	public void setDocCategory(final String docCategory) {
		this.docCategory = docCategory;
	}

	/**
	 * @return the filenetReferenceId
	 */
	public String getFilenetReferenceId() {
		return filenetReferenceId;
	}

	/**
	 * @param filenetReferenceId
	 *            the filenetReferenceId to set
	 */
	public void setFilenetReferenceId(final String filenetReferenceId) {
		this.filenetReferenceId = filenetReferenceId;
	}

	/**
	 * @return the userFullName
	 */
	public String getUserFullName() {
		return userFullName;
	}

	/**
	 * @param userFullName
	 *            the documentName to set
	 */
	public void setUserFullName(final String userFullName) {
		this.userFullName = userFullName;
	}

	/**
	 * @return the documentName
	 */
	public String getDocumentName() {
		return documentName;
	}

	/**
	 * @param documentName
	 *            the documentName to set
	 */
	public void setDocumentName(final String documentName) {
		this.documentName = documentName;
	}

	/**
	 * @return the documentLocation
	 */
	public String getDocumentLocation() {
		return documentLocation;
	}

	/**
	 * @param documentLocation
	 *            the documentLocation to set
	 */
	public void setDocumentLocation(final String documentLocation) {
		this.documentLocation = documentLocation;
	}

	/**
	 * @return the documentStream
	 */
	public InputStream getDocumentStream() {
		return documentStream;
	}

	/**
	 * @param documentStream
	 *            the documentStream to set
	 */
	public void setDocumentStream(final InputStream documentStream) {
		this.documentStream = documentStream;
	}

	/**
	 * @return the appGroup
	 */
	public String getAppGroup() {
		return appGroup;
	}

	/**
	 * @param appGroup
	 *            the appGroup to set
	 */
	public void setAppGroup(final String appGroup) {
		this.appGroup = appGroup;
	}

	/**
	 * @return the filenetMetadata
	 */
	public HashMap<String, String> getFilenetMetadata() {
		return filenetMetadata;
	}

}
