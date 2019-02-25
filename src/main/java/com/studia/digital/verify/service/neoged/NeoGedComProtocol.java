package com.studia.digital.verify.service.neoged;

public final class NeoGedComProtocol {
	
	public static final String NEOGED_USER = "DOMINIQUE";
	public static final String NEOGED_PSW = "1703850244";
	public static final String NEOGED_DB = "NEOGED";
	
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
		NEOGED_GET_DOC("getDoc(_id=%s)");
		
		private String value;

		private Command(String value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return value;
		}
	}

}
