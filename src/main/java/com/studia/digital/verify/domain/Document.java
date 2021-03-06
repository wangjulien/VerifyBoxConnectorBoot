package com.studia.digital.verify.domain;

/**
 * Business Object represent a Document 
 * 
 * @author Jiliang.WANG
 *
 */
public class Document {
	private String docId;
	private String title;
	private String contenuBase64;
	private String mimeType;

	public Document() {
		super();
	}

	public Document(String docId, String title, String contenuBase64, String mimeType) {
		super();
		this.docId = docId;
		this.title = title;
		this.contenuBase64 = contenuBase64;
		this.mimeType = mimeType;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContenuBase64() {
		return contenuBase64;
	}

	public void setContenuBase64(String contenuBase64) {
		this.contenuBase64 = contenuBase64;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

}
