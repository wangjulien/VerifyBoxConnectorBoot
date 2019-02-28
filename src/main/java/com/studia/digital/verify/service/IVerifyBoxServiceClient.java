package com.studia.digital.verify.service;

import com.lexpersona.lp7verifybox.server.jaxb.ValidationPolicyRequestType;
import com.lexpersona.lp7verifybox.server.jaxb.ValidationPolicyResponseType;
import com.lexpersona.lp7verifybox.server.jaxb.VerificationLogRequestType;
import com.lexpersona.lp7verifybox.server.jaxb.VerificationLogResponseType;
import com.lexpersona.lp7verifybox.server.jaxb.VerifyRequestType;
import com.lexpersona.lp7verifybox.server.jaxb.VerifyResponseType;

/**
 * Client that in charge of communication with Signature checher service, backed with SOAP and WebServiceTemplate
 * 
 * @author Jiliang.WANG
 *
 */
public interface IVerifyBoxServiceClient {

	/**
	 * Verify the signature of a document
	 * 
	 * @param verifyRequestType : contains a document and its signatrue (internal/external)
	 * @return VerifyResponseType : contains verification result and a Token file in XML format
	 */
	VerifyResponseType verifySignature(VerifyRequestType verifyRequestType);

	/**
	 * Get all the log form the server
	 * 
	 * @param verificationLogRequestType
	 * @return
	 */
	VerificationLogResponseType getVerificationLogs(VerificationLogRequestType verificationLogRequestType);

	/**
	 * Get all the validation policies used by the server
	 * 
	 * @param validationPolicyRequestType
	 * @return
	 */
	ValidationPolicyResponseType getValidationPolicies(ValidationPolicyRequestType validationPolicyRequestType);
}
