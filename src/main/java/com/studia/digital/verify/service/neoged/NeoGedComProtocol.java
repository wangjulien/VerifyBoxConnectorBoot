package com.studia.digital.verify.service.neoged;

/**
 * Constant used in the communication with Neoged
 * 
 * @author Jiliang.WANG
 *
 */
public final class NeoGedComProtocol {
	
	public static final String NEOGED_USER = "DOMINIQUE";
	public static final String NEOGED_PSW = "1703850244";
	public static final String NEOGED_DB = "NEOGED";
	
	public static final String ELASTIC_REQUEST_FOR_SEARCH = "docsnom,description,docsfile,comment,workflowfrom,htmldata,pivotdata.content=neoged";
	public static final String ELASTIC_FIELDS_FOR_SEARCH = "docsnom";
	
	private NeoGedComProtocol() {
		super();
		throw new AssertionError("Instantiation not allowed!");
	}

	public static enum ElasticType {
		DOCUMENT("documents");

		private String value;

		private ElasticType(String value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return value;
		}

	}
	
	public static enum Command {
		NEOGED_GET_DOC("getDoc(_id=%s)"),
		NEOGED_SEARCH("search(_id=%s)"),
		NEOGED_PUT("put()"),
		NEOGED_ATTACH_DOCUMENTS("attachDocuments()");
		
		private String value;

		private Command(String value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return value;
		}
	}
	
	public static enum ReturnCode {
		OK("OK"),
		KO("KO");
		
		private String value;

		private ReturnCode(String value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return value;
		}
	}
}
