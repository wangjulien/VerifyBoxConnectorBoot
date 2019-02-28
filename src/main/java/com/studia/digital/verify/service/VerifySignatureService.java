package com.studia.digital.verify.service;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.lexpersona.lp7verifybox.server.jaxb.Base64DataType;
import com.lexpersona.lp7verifybox.server.jaxb.DocumentType;
import com.lexpersona.lp7verifybox.server.jaxb.DocumentsType;
import com.lexpersona.lp7verifybox.server.jaxb.ObjectFactory;
import com.lexpersona.lp7verifybox.server.jaxb.OptionalInputsType;
import com.lexpersona.lp7verifybox.server.jaxb.OutputModeType;
import com.lexpersona.lp7verifybox.server.jaxb.OutputSettingsType;
import com.lexpersona.lp7verifybox.server.jaxb.RequestedDataType;
import com.lexpersona.lp7verifybox.server.jaxb.StatusType;
import com.lexpersona.lp7verifybox.server.jaxb.VerifyRequestType;
import com.lexpersona.lp7verifybox.server.jaxb.VerifyResponseType;
import com.studia.digital.verify.domain.Document;

/**
 * Implementation of @IVerifySignatureService
 * 
 * @author Jiliang.WANG
 *
 */
@Service
public class VerifySignatureService implements IVerifySignatureService {

	/** JAXB object factory */
	private final ObjectFactory objectFactory = new ObjectFactory();

	private final IVerifyBoxServiceClient verifyBoxServiceClient;

	public VerifySignatureService(IVerifyBoxServiceClient verifyBoxServiceClient) {
		super();
		this.verifyBoxServiceClient = verifyBoxServiceClient;
	}

	@Override
	public Entry<StatusType, String> verifyInternalSignature(final Document document) {
		//
		// Content of poof to be verified
		//
		Base64DataType base64Content = objectFactory.createBase64DataType();
		base64Content.setBase64Value(document.getContenuBase64());

		DocumentType documentType = objectFactory.createDocumentType();
		documentType.setBase64Data(base64Content);
		documentType.setID(document.getDocId());

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
		verifyRequestType.setRequestID(String.valueOf(document.getDocId()));
		verifyRequestType.setEvidenceObject(documentType);
		verifyRequestType.setOptionalInputs(options);

		VerifyResponseType response = verifyBoxServiceClient.verifySignature(verifyRequestType);
		
		//
		// TODO: Extraction verification result information
		//
		
		// Extraction of Token
		String tokenBase64 = response.getOptionalOutputs().getVerificationToken().getBase64Data().getBase64Value();
		StatusType resultStatus = response.getResult().getVerificationStatus();

		return new SimpleEntry<>(resultStatus,tokenBase64);
	}

	@Override
	public Entry<StatusType, String> verifyExternalSignature(final Document document, final Document signature) {
		//
		// Content of poof to be verified
		//

		Base64DataType base64Content = objectFactory.createBase64DataType();
		base64Content.setBase64Value(document.getContenuBase64());

		Base64DataType base64SignatureContenu = objectFactory.createBase64DataType();
		base64SignatureContenu.setBase64Value(signature.getContenuBase64());

		DocumentType documentType = objectFactory.createDocumentType();
		documentType.setBase64Data(base64Content);
		documentType.setID(document.getDocId());

		DocumentType proof = objectFactory.createDocumentType();
		proof.setBase64Data(base64SignatureContenu);
		proof.setID(signature.getDocId());

		//
		// Output Options
		//
		OutputSettingsType outputSetting = objectFactory.createOutputSettingsType();
		outputSetting.setMode(OutputModeType.BASE_64);

		RequestedDataType requestData = objectFactory.createRequestedDataType();
		requestData.setVerificationToken(outputSetting);

		DocumentsType supportDocuments = objectFactory.createDocumentsType();
		supportDocuments.getDocument().add(documentType);

		OptionalInputsType options = objectFactory.createOptionalInputsType();
		options.setRequestedData(requestData);
		options.setSupportingDocuments(supportDocuments);

		//
		// Request
		//
		VerifyRequestType verifyRequestType = objectFactory.createVerifyRequestType();
		verifyRequestType.setRequestID(String.valueOf(document.getDocId()));
		verifyRequestType.setEvidenceObject(proof);
		verifyRequestType.setOptionalInputs(options);

		VerifyResponseType response = verifyBoxServiceClient.verifySignature(verifyRequestType);

		// Extraction of Token
		String tokenBase64 = response.getOptionalOutputs().getVerificationToken().getBase64Data().getBase64Value();
		StatusType resultStatus = response.getResult().getVerificationStatus();

		return new SimpleEntry<>(resultStatus,tokenBase64);
	}

}
