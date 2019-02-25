package com.studia.digital.verify.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.lexpersona.lp7verifybox.server.jaxb.Base64DataType;
import com.lexpersona.lp7verifybox.server.jaxb.DocumentType;
import com.lexpersona.lp7verifybox.server.jaxb.DocumentsType;
import com.lexpersona.lp7verifybox.server.jaxb.ObjectFactory;
import com.lexpersona.lp7verifybox.server.jaxb.OptionalInputsType;
import com.lexpersona.lp7verifybox.server.jaxb.OutputModeType;
import com.lexpersona.lp7verifybox.server.jaxb.OutputSettingsType;
import com.lexpersona.lp7verifybox.server.jaxb.RequestedDataType;
import com.lexpersona.lp7verifybox.server.jaxb.StatusType;
import com.lexpersona.lp7verifybox.server.jaxb.ValidationPolicyRequestType;
import com.lexpersona.lp7verifybox.server.jaxb.ValidationPolicyResponseType;
import com.lexpersona.lp7verifybox.server.jaxb.VerificationLogRequestType;
import com.lexpersona.lp7verifybox.server.jaxb.VerificationLogResponseType;
import com.lexpersona.lp7verifybox.server.jaxb.VerifyRequestType;
import com.lexpersona.lp7verifybox.server.jaxb.VerifyResponseType;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TestVerifyServiceClient {

	/** JAXB object factory */
	private final ObjectFactory objectFactory = new ObjectFactory();

	private final String SIGNED_DOC_FILE = "classpath:FicheValidation.pdf";
	private final String DOC_FILE = "classpath:1-Intérêt collectif et intérêt individuel.docx";
	private final String SIGNATURE_FILE = "classpath:1-Interetcollectifetinteretindividuel.docx.xml";

	@Autowired
	private ResourceLoader resourceLoader;

	@Autowired
	private IVerifyBoxServiceClient verifyServiceClient;

	@Test
	void should_send_doc_for_verify() {
		VerifyRequestType verifyRequestType = objectFactory.createVerifyRequestType();
		verifyRequestType.setRequestID("TestRequestID");

		VerifyResponseType response = verifyServiceClient.verifySignature(verifyRequestType);

		assertEquals(StatusType.INCOMPLETE, response.getResult().getVerificationStatus());
	}

	@Test
	void should_send_and_verify_doc_with_internal_signature() throws IOException {
		//
		// Content of poof to be verified
		//
		byte[] bytes = Files.readAllBytes(resourceLoader.getResource(SIGNED_DOC_FILE).getFile().toPath());

		String contenu = Base64.getEncoder().encodeToString(bytes);
		Base64DataType base64Content = objectFactory.createBase64DataType();
		base64Content.setBase64Value(contenu);

		// Files.write(Paths.get("C:/Dev/base64.txt"), contenu.getBytes(),
		// StandardOpenOption.CREATE);

		DocumentType document = objectFactory.createDocumentType();
		document.setBase64Data(base64Content);
		// document.setPlainData(plainData);
		document.setID(UUID.randomUUID().toString());

		//
		// Output Options
		//

		OutputSettingsType outputSetting = objectFactory.createOutputSettingsType();
		outputSetting.setMode(OutputModeType.BASE_64);

		RequestedDataType requestData = objectFactory.createRequestedDataType();
		requestData.setVerificationToken(outputSetting);

		OptionalInputsType options = objectFactory.createOptionalInputsType();
		options.setRequestedData(requestData);

		// Request

		VerifyRequestType verifyRequestType = objectFactory.createVerifyRequestType();
		verifyRequestType.setRequestID("TestRequestID");
		verifyRequestType.setEvidenceObject(document);
		verifyRequestType.setOptionalInputs(options);

		VerifyResponseType response = verifyServiceClient.verifySignature(verifyRequestType);

		assertEquals(StatusType.PASSED, response.getResult().getVerificationStatus());

		// Extraction of Token
		String tokenBase64 = response.getOptionalOutputs().getVerificationToken().getBase64Data().getBase64Value();
		assertNotNull(tokenBase64);

		// System.err.println(tokenBase64);
		// System.err.println(new String(Base64.getDecoder().decode(tokenBase64),
		// StandardCharsets.UTF_8));
	}

	@Test
	void should_send_and_verify_doc_with_external_signature() throws IOException {
		//
		// Content of poof to be verified
		//
		byte[] bytes = Files.readAllBytes(resourceLoader.getResource(DOC_FILE).getFile().toPath());
		byte[] signature = Files.readAllBytes(resourceLoader.getResource(SIGNATURE_FILE).getFile().toPath());

		String contenu = Base64.getEncoder().encodeToString(bytes);
		Base64DataType base64Content = objectFactory.createBase64DataType();
		base64Content.setBase64Value(contenu);
		
		String signatureContenu = Base64.getEncoder().encodeToString(signature);
		Base64DataType base64SignatureContenu = objectFactory.createBase64DataType();
		base64SignatureContenu.setBase64Value(signatureContenu);

		DocumentType document = objectFactory.createDocumentType();
		document.setBase64Data(base64Content);
		document.setID(UUID.randomUUID().toString());
		
		DocumentType proof = objectFactory.createDocumentType();
		proof.setBase64Data(base64SignatureContenu);
		proof.setID(UUID.randomUUID().toString());

		//
		// Output Options
		//
		OutputSettingsType outputSetting = objectFactory.createOutputSettingsType();
		outputSetting.setMode(OutputModeType.BASE_64);

		RequestedDataType requestData = objectFactory.createRequestedDataType();
		requestData.setVerificationToken(outputSetting);
		
		DocumentsType supportDocuments = objectFactory.createDocumentsType();
		supportDocuments.getDocument().add(document);
		
		OptionalInputsType options = objectFactory.createOptionalInputsType();
		options.setRequestedData(requestData);
		options.setSupportingDocuments(supportDocuments);
		
		//
		// Request
		//
		VerifyRequestType verifyRequestType = objectFactory.createVerifyRequestType();
		verifyRequestType.setRequestID("TestRequestID");
		verifyRequestType.setEvidenceObject(proof);
		verifyRequestType.setOptionalInputs(options);

		VerifyResponseType response = verifyServiceClient.verifySignature(verifyRequestType);
		
		// Extraction of Token
		String tokenBase64 = response.getOptionalOutputs().getVerificationToken().getBase64Data().getBase64Value();
		assertNotNull(tokenBase64);
		
		// System.err.println(new String(Base64.getDecoder().decode(tokenBase64.getBytes())));

		System.err.println(response.getResult().getMajorCode() + " " + response.getResult().getMinorCode() + " "
				+ response.getResult().getMessage());

		assertEquals(StatusType.PASSED, response.getResult().getVerificationStatus());
	}

	@Test
	void should_get_verification_logs() {
		VerificationLogRequestType verificationLogRequestType = objectFactory.createVerificationLogRequestType();
		verificationLogRequestType.setRequestID("TestRequestID");

		VerificationLogResponseType response = verifyServiceClient.getVerificationLogs(verificationLogRequestType);

		assertEquals(0, response.getVerificationLog().size());
	}

	@Test
	void should_get_verification_policies() {
		ValidationPolicyRequestType validationPolicyRequestType = objectFactory.createValidationPolicyRequestType();
		validationPolicyRequestType.setRequestID("TestRequestID");

		ValidationPolicyResponseType response = verifyServiceClient.getValidationPolicies(validationPolicyRequestType);

		assertEquals(2, response.getValidationPolicy().size());
	}
}
