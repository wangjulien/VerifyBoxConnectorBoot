package com.studia.digital.verify.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.studia.digital.verify.service.neoged.NeoGedComProtocol.ElasticType;

@JsonInclude(Include.NON_NULL)
public class NeoGedRequestMsg implements Serializable {

	public static class Builder {

		private String elasticCommand;
		private String elasticType;
		private boolean getasbase64;
		private String fileContent;
		private Integer elasticTaille;
		private String elasticContentType;
		private String elasticDocName;

		private String user;
		private String encryptedPassword;
		private String mailid;
		private String nomBase;
		private String mailOwner;

		public Builder() {
			super();
			this.elasticContentType = ElasticType.DOCUMENT.toString();
			this.getasbase64 = true;
		}

		public Builder setElasticCommand(String elasticCommand) {
			this.elasticCommand = elasticCommand;
			return this;
		}

		public Builder setElasticType(String elasticType) {
			this.elasticType = elasticType;
			return this;
		}

		public Builder setGetasbase64(boolean getasbase64) {
			this.getasbase64 = getasbase64;
			return this;
		}

		public Builder setFileContent(String fileContent) {
			this.fileContent = fileContent;
			return this;
		}

		public Builder setElasticTaille(Integer elasticTaille) {
			this.elasticTaille = elasticTaille;
			return this;
		}

		public Builder setElasticContentType(String elasticContentType) {
			this.elasticContentType = elasticContentType;
			return this;
		}

		public Builder setElasticDocName(String elasticDocName) {
			this.elasticDocName = elasticDocName;
			return this;
		}

		public Builder setUser(String user) {
			this.user = user;
			return this;
		}

		public Builder setEncryptedPassword(String encryptedPassword) {
			this.encryptedPassword = encryptedPassword;
			return this;
		}

		public Builder setMailid(String mailid) {
			this.mailid = mailid;
			return this;
		}

		public Builder setNomBase(String nomBase) {
			this.nomBase = nomBase;
			return this;
		}

		public Builder setMailOwner(String mailOwner) {
			this.mailOwner = mailOwner;
			return this;
		}

		public NeoGedRequestMsg build() {
			return new NeoGedRequestMsg(this);
		}

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1627975033249720435L;

	private String elasticCommand;
	private String elasticType;
	private boolean getasbase64;
	private String fileContent;
	private Integer elasticTaille;
	private String elasticContentType;
	private String elasticDocName;

	private String user;
	private String encryptedPassword;
	private String mailid;
	private String nomBase;
	private String mailOwner;

	private NeoGedRequestMsg(Builder builder) {
		super();
		this.elasticCommand = builder.elasticCommand;
		this.elasticType = builder.elasticType;
		this.getasbase64 = builder.getasbase64;
		this.fileContent = builder.fileContent;
		this.elasticTaille = builder.elasticTaille;
		this.elasticContentType = builder.elasticContentType;
		this.elasticDocName = builder.elasticDocName;
		this.user = builder.user;
		this.encryptedPassword = builder.encryptedPassword;
		this.mailid = builder.mailid;
		this.nomBase = builder.nomBase;
		this.mailOwner = builder.mailOwner;
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

	public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

	public Integer getElasticTaille() {
		return elasticTaille;
	}

	public void setElasticTaille(Integer elasticTaille) {
		this.elasticTaille = elasticTaille;
	}

	public String getElasticContentType() {
		return elasticContentType;
	}

	public void setElasticContentType(String elasticContentType) {
		this.elasticContentType = elasticContentType;
	}

	public String getElasticDocName() {
		return elasticDocName;
	}

	public void setElasticDocName(String elasticDocName) {
		this.elasticDocName = elasticDocName;
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
