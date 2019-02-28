package com.studia.digital.verify.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.studia.digital.verify.service.neoged.NeoGedComProtocol.ElasticType;

/**
 * class of NeoGed request, using for JSON - Object mapping. It group all
 * possible NoeGed request used in this App, so certain attributes may be
 * Nullable. Jackson will not parser/string null attributes.
 * 
 * So test Null of the attributes before use.
 * 
 * Use the builder to create the request for Neoged
 * 
 * @author Jiliang.WANG
 *
 */
@JsonInclude(Include.NON_NULL)
public class NeoGedRequestMsg implements Serializable {

	private static final String MAIL_ID = "dgerbod@gmail.com";

	public static class Builder {

		private String elasticCommand;
		private String elasticType;
		private Boolean getasbase64;
		private Integer secuLevel;

		private String fileContent;
		private Integer elasticTaille;
		private String elasticContentType;
		private String elasticDocName;
		private String elasticId;
		private String documentsList;

		private String elasticRequest;
		private String elasticFields;
		private String versionfilter;

		private String user;
		private String encryptedPassword;
		private String mailid;
		private String nomBase;
		private String mailOwner;

		public Builder() {
			super();
			this.elasticType = ElasticType.DOCUMENT.toString();
			this.getasbase64 = true;
			this.mailid = MAIL_ID;
			this.mailOwner = MAIL_ID;
			this.secuLevel = 9; // Security 9 means this App can read any file in Neoged
		}

		public Builder setElasticCommand(String elasticCommand) {
			this.elasticCommand = elasticCommand;
			return this;
		}

		public Builder setElasticType(String elasticType) {
			this.elasticType = elasticType;
			return this;
		}

		public Builder setGetasbase64(Boolean getasbase64) {
			this.getasbase64 = getasbase64;
			return this;
		}

		public Builder setSecuLevel(Integer secuLevel) {
			this.secuLevel = secuLevel;
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

		public Builder setElasticId(String elasticId) {
			this.elasticId = elasticId;
			return this;
		}

		public Builder setDocumentsList(String documentsList) {
			this.documentsList = documentsList;
			return this;
		}

		public Builder setElasticRequest(String elasticRequest) {
			this.elasticRequest = elasticRequest;
			return this;
		}

		public Builder setElasticFields(String elasticFields) {
			this.elasticFields = elasticFields;
			return this;
		}

		public Builder setVersionfilter(String versionfilter) {
			this.versionfilter = versionfilter;
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
	private Boolean getasbase64;
	private Integer secuLevel;

	private String fileContent;
	private Integer elasticTaille;
	private String elasticContentType;
	private String elasticDocName;
	@JsonProperty("elasticid")
	private String elasticId;
	@JsonProperty("documents_list")
	private String documentsList;

	private String elasticRequest;
	private String elasticFields;
	private String versionfilter;

	private String user;
	private String encryptedPassword;
	private String mailid;
	private String nomBase;
	private String mailOwner;

	public NeoGedRequestMsg() {
		super();
	}

	private NeoGedRequestMsg(Builder builder) {
		super();
		this.elasticCommand = builder.elasticCommand;
		this.elasticType = builder.elasticType;
		this.getasbase64 = builder.getasbase64;
		this.secuLevel = builder.secuLevel;
		this.fileContent = builder.fileContent;
		this.elasticTaille = builder.elasticTaille;
		this.elasticContentType = builder.elasticContentType;
		this.elasticDocName = builder.elasticDocName;
		this.elasticId = builder.elasticId;
		this.documentsList = builder.documentsList;
		this.elasticRequest = builder.elasticRequest;
		this.elasticFields = builder.elasticFields;
		this.versionfilter = builder.versionfilter;
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

	public Boolean isGetasbase64() {
		return getasbase64;
	}

	public void setGetasbase64(Boolean getasbase64) {
		this.getasbase64 = getasbase64;
	}

	public Integer getSecuLevel() {
		return secuLevel;
	}

	public void setSecuLevel(Integer secuLevel) {
		this.secuLevel = secuLevel;
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

	public String getElasticId() {
		return elasticId;
	}

	public void setElasticId(String elasticId) {
		this.elasticId = elasticId;
	}

	public String getDocumentsList() {
		return documentsList;
	}

	public void setDocumentsList(String documentsList) {
		this.documentsList = documentsList;
	}

	public String getElasticRequest() {
		return elasticRequest;
	}

	public void setElasticRequest(String elasticRequest) {
		this.elasticRequest = elasticRequest;
	}

	public String getElasticFields() {
		return elasticFields;
	}

	public void setElasticFields(String elasticFields) {
		this.elasticFields = elasticFields;
	}

	public String getVersionfilter() {
		return versionfilter;
	}

	public void setVersionfilter(String versionfilter) {
		this.versionfilter = versionfilter;
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
