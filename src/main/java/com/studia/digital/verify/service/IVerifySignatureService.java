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

	Entry<StatusType, String> verifyInternalSignature(final Document document);

	Entry<StatusType, String> verifyExternalSignature(Document document, Document signature);
}
