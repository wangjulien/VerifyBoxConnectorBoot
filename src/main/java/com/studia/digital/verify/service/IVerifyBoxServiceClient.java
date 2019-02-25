package com.studia.digital.verify.service;

import com.lexpersona.lp7verifybox.server.jaxb.ValidationPolicyRequestType;
import com.lexpersona.lp7verifybox.server.jaxb.ValidationPolicyResponseType;
import com.lexpersona.lp7verifybox.server.jaxb.VerificationLogRequestType;
import com.lexpersona.lp7verifybox.server.jaxb.VerificationLogResponseType;
import com.lexpersona.lp7verifybox.server.jaxb.VerifyRequestType;
import com.lexpersona.lp7verifybox.server.jaxb.VerifyResponseType;

public interface IVerifyBoxServiceClient {

	VerifyResponseType verifySignature(VerifyRequestType verifyRequestType);

	VerificationLogResponseType getVerificationLogs(VerificationLogRequestType verificationLogRequestType);

	ValidationPolicyResponseType getValidationPolicies(ValidationPolicyRequestType validationPolicyRequestType);
}
