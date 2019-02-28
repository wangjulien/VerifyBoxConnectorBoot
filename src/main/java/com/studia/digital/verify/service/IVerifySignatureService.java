package com.studia.digital.verify.service;


import java.util.Map.Entry;

import com.lexpersona.lp7verifybox.server.jaxb.StatusType;
import com.studia.digital.verify.domain.Document;

/**
 * Facet class which verify a document with/without proof files 
 * 
 * @author Jiliang.WANG
 *
 */
public interface IVerifySignatureService {

	/**
	 * Document which contains signature internally
	 * 
	 * @param document : document to be verified
	 * @return : verification result status and the Token file encoded by base64
	 */
	Entry<StatusType, String> verifyInternalSignature(final Document document);

	/**
	 * Document to be verified with its external signature
	 * 
	 * @param document : document to be verified
	 * @param signature : its signature file
	 * @return
	 */
	Entry<StatusType, String> verifyExternalSignature(Document document, Document signature);
}
