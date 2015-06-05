package com.scb.wb.document.model;

import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;

/**
 * 
 * Fix Me!
 *
 */
public class FilenetDocumentRequest implements Serializable {

	private static final long serialVersionUID = 1329394228453430607L;

	/**
	 * 
	 */
	private String documentId;

	/**
	 * Once the file has been uploaded successfully, File-net creates a Unique ID for the future reference and which
	 * will be used to identify the Document in File-net server.
	 */
	private String filenetReferenceId;

	/**
	 * This field used to define the document name.
	 */
	private String documentName;

	/**
	 * 
	 */
	private String documentLocation;

	/**
	 * 
	 */
	private InputStream documentStream;

	/**
	 * 
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
	 * @return the filenetMetadata
	 */
	public HashMap<String, String> getFilenetMetadata() {
		return filenetMetadata;
	}

}