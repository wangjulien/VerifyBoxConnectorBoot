package com.studia.digital.verify.service;

import com.studia.digital.verify.domain.Document;

/**
 * Facet class which verify a document with/without proof files
 * 
 * @author Jiliang.WANG
 *
 */
public interface IVerifySignatureService {

	String verifyInternalSignature(final Document document);
	
	String verifyExternalSignature(Document document, Document signature);
}
