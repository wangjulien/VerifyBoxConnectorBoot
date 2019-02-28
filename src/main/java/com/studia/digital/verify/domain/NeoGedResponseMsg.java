package com.studia.digital.verify.domain;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Class is abstraction of NeoGed result response. The attributes are nullable
 * 
 * @author Jiliang.WANG
 *
 */
@JsonInclude(Include.NON_NULL)
public class NeoGedResponseMsg implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8712800735692190596L;

	private List<List<List<String>>> data;
	private String messageErreur;
	private Boolean last;
	private String codeRetour;
	private Integer maxSize;
	private String documentID;
	private String nonStoredList;

	public NeoGedResponseMsg() {
		super();
	}

	public NeoGedResponseMsg(List<List<List<String>>> data, String messageErreur, Boolean last, String codeRetour,
			Integer maxSize, String documentID, String nonStoredList) {
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

	public Boolean isLast() {
		return last;
	}

	public void setLast(Boolean last) {
		this.last = last;
	}

	public String getCodeRetour() {
		return codeRetour;
	}

	public void setCodeRetour(String codeRetour) {
		this.codeRetour = codeRetour;
	}

	public Integer getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(Integer maxSize) {
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

	@Override
	public String toString() {
		return "NeoGedResponseMsg [data=" + data.toString() + ", messageErreur=" + messageErreur + ", last=" + last
				+ ", codeRetour=" + codeRetour + ", maxSize=" + maxSize + ", documentID=" + documentID
				+ ", nonStoredList=" + nonStoredList + "]";
	}

}
