package com.studia.digital.verify.service.neoged;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.studia.digital.verify.domain.Document;
import com.studia.digital.verify.domain.NeoGedRequestMsg;
import com.studia.digital.verify.domain.NeoGedResponseMsg;
import com.studia.digital.verify.service.neoged.NeoGedComProtocol.Command;
import com.studia.digital.verify.service.neoged.NeoGedComProtocol.ElasticType;

@Service
public class NeoGedDocFacet {
	private static final Logger LOGGER = LoggerFactory.getLogger(NeoGedDocFacet.class);

	@Value("${app.url.neoged-host:https://neoged.telino.fr/cdmsserveur/startElasticXML}")
	private String neoGedHostUrl;

	private final RestTemplate restTemplate;

	public NeoGedDocFacet(RestTemplate restTemplate) {
		super();
		this.restTemplate = restTemplate;
	}

	public Document getDocumentById(final String docId, final String user, final String encryptedPassword,
			final String nomBase) {

		NeoGedRequestMsg getDocMessage = new NeoGedRequestMsg();
		getDocMessage.setElasticCommand(String.format(Command.NEOGED_GET_DOC.toString(), docId));
		getDocMessage.setElasticType(ElasticType.DOCUMENT.toString());
		getDocMessage.setGetasbase64(true);
		getDocMessage.setUser(user);
		getDocMessage.setEncryptedPassword(encryptedPassword);
		getDocMessage.setNomBase(nomBase);
		getDocMessage.setMailid("");
		getDocMessage.setMailOwner("");

		HttpEntity<NeoGedRequestMsg> request = new HttpEntity<>(getDocMessage);

		ResponseEntity<NeoGedResponseMsg> response = restTemplate.postForEntity(neoGedHostUrl, request,
				NeoGedResponseMsg.class);

		Document doc = new Document();
		doc.setDocId(docId);

		if (response.getStatusCode() == HttpStatus.OK) {
			if ("OK".equals(response.getBody().getCodeRetour())) {
				// Extract document information from result
				doc.setContenuBase64(response.getBody().getData().get(0).get(0).get(0));
			} else {
				LOGGER.error("NeoGed response error : " + response.getBody().getCodeRetour() + "\n"
						+ response.getBody().getMessageErreur());
			}

		} else {
			LOGGER.error("HTTP error : " + response.getStatusCode());
		}

		return doc;
	}

}
