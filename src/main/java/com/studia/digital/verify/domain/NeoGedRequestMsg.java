package com.studia.digital.verify.domain;

import java.io.Serializable;

public class NeoGedRequestMsg implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8945141339506705642L;
	
	private String elasticCommand;
	private String elasticType;
	private boolean getasbase64;
	private String user;
	private String encryptedPassword;
	private String mailid;
	private String nomBase;
	private String mailOwner;
	
	public NeoGedRequestMsg() {
		super();
	}

	public NeoGedRequestMsg(String elasticCommand, String elasticType, boolean getasbase64, String user,
			String encryptedPassword, String mailid, String nomBase, String mailOwner) {
		super();
		this.elasticCommand = elasticCommand;
		this.elasticType = elasticType;
		this.getasbase64 = getasbase64;
		this.user = user;
		this.encryptedPassword = encryptedPassword;
		this.mailid = mailid;
		this.nomBase = nomBase;
		this.mailOwner = mailOwner;
	}

	public String getElasticCommand() {
		return elasticCommand;
	}

	public void setElasticCommand(String elasticCommand) {
		this.elasticCommand = elasticCommand;
	}

	public String getElasticType() {
		return elasticType;
	}

	public void setElasticType(String elasticType) {
		this.elasticType = elasticType;
	}

	public boolean isGetasbase64() {
		return getasbase64;
	}

	public void setGetasbase64(boolean getasbase64) {
		this.getasbase64 = getasbase64;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public String getMailid() {
		return mailid;
	}

	public void setMailid(String mailid) {
		this.mailid = mailid;
	}

	public String getNomBase() {
		return nomBase;
	}

	public void setNomBase(String nomBase) {
		this.nomBase = nomBase;
	}

	public String getMailOwner() {
		return mailOwner;
	}

	public void setMailOwner(String mailOwner) {
		this.mailOwner = mailOwner;
	}
	
}
