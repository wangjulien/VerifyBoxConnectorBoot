package com.studia.digital.verify.service;

import javax.xml.bind.JAXBElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.lexpersona.lp7verifybox.server.jaxb.ValidationPolicyRequestType;
import com.lexpersona.lp7verifybox.server.jaxb.ValidationPolicyResponseType;
import com.lexpersona.lp7verifybox.server.jaxb.VerificationLogRequestType;
import com.lexpersona.lp7verifybox.server.jaxb.VerificationLogResponseType;
import com.lexpersona.lp7verifybox.server.jaxb.VerifyRequestType;
import com.lexpersona.lp7verifybox.server.jaxb.VerifyResponseType;
import com.lexpersona.lp7verifybox.server.ws.ObjectFactory;

/**
 * Implementation of @IVerifyBoxServiceClient
 * 
 * @author Jiliang.WANG
 *
 */
public class VerifyBoxServiceClient extends WebServiceGatewaySupport implements IVerifyBoxServiceClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(VerifyBoxServiceClient.class);

	private final ObjectFactory wsObjectFactory = new ObjectFactory();

	@Override
	public VerifyResponseType verifySignature(VerifyRequestType verifyRequestType) {

		@SuppressWarnings("unchecked")
		VerifyResponseType response = ((JAXBElement<VerifyResponseType>) getWebServiceTemplate().marshalSendAndReceive(
				wsObjectFactory.createVerifyRequest(verifyRequestType), new SoapActionCallback(""))).getValue();

		LOGGER.debug(response.getRequestID() + " " + response.getResult().getMessage());
		// LOGGER.debug(new String(Base64.getDecoder().decode(response.getOptionalOutputs().getVerificationToken().getBase64Data().getBase64Value())));
		return response;
	}

	@Override
	public VerificationLogResponseType getVerificationLogs(VerificationLogRequestType verificationLogRequestType) {

		@SuppressWarnings("unchecked")
		VerificationLogResponseType response = ((JAXBElement<VerificationLogResponseType>) getWebServiceTemplate()
				.marshalSendAndReceive(wsObjectFactory.createVerificationLogRequest(verificationLogRequestType),
						new SoapActionCallback(""))).getValue();

		LOGGER.debug(response.getRequestID() + " " + response.getVerificationLog().toString());
		return response;
	}

	@Override
	public ValidationPolicyResponseType getValidationPolicies(ValidationPolicyRequestType validationPolicyRequestType) {

		@SuppressWarnings("unchecked")
		ValidationPolicyResponseType response = ((JAXBElement<ValidationPolicyResponseType>) getWebServiceTemplate()
				.marshalSendAndReceive(wsObjectFactory.createValidationPolicyRequest(validationPolicyRequestType),
						new SoapActionCallback(""))).getValue();

		LOGGER.debug(response.getRequestID() + " " + response.getValidationPolicy().toString());
		return response;
	}
}
