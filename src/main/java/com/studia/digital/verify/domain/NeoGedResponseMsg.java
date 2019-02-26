package com.studia.digital.verify.domain;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class NeoGedResponseMsg implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8712800735692190596L;

	private List<List<List<String>>> data;
	private String messageErreur;
	private boolean last;
	private String codeRetour;
	private int maxSize;
	private String documentID;
	private String nonStoredList;

	public NeoGedResponseMsg() {
		super();
	}

	public NeoGedResponseMsg(List<List<List<String>>> data, String messageErreur, boolean last, String codeRetour,
			int maxSize, String documentID, String nonStoredList) {
		super();
		this.data = data;
		this.messageErreur = messageErreur;
		this.last = last;
		this.codeRetour = codeRetour;
		this.maxSize = maxSize;
		this.documentID = documentID;
		this.nonStoredList = nonStoredList;
	}

	public List<List<List<String>>> getData() {
		return data;
	}

	public void setData(List<List<List<String>>> data) {
		this.data = data;
	}

	public String getMessageErreur() {
		return messageErreur;
	}

	public void setMessageErreur(String messageErreur) {
		this.messageErreur = messageErreur;
	}

	public boolean isLast() {
		return last;
	}

	public void setLast(boolean last) {
		this.last = last;
	}

	public String getCodeRetour() {
		return codeRetour;
	}

	public void setCodeRetour(String codeRetour) {
		this.codeRetour = codeRetour;
	}

	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	public String getDocumentID() {
		return documentID;
	}

	public void setDocumentID(String documentID) {
		this.documentID = documentID;
	}

	public String getNonStoredList() {
		return nonStoredList;
	}

	public void setNonStoredList(String nonStoredList) {
		this.nonStoredList = nonStoredList;
	}
}
